// Minimal PDF text extractor: inflate streams, decode ToUnicode CMaps, walk content streams.
const fs = require('fs'), zlib = require('zlib');
const file = process.argv[2];
const buf = fs.readFileSync(file);
const s = buf.toString('latin1');

function findObjects(str) {
  const objs = {};
  const re = /(\d+)\s+(\d+)\s+obj\b/g;
  let m;
  const starts = [];
  while ((m = re.exec(str))) starts.push({ num: +m[1], pos: m.index, bodyStart: re.lastIndex });
  for (let i = 0; i < starts.length; i++) {
    const end = str.indexOf('endobj', starts[i].bodyStart);
    if (end < 0) continue;
    objs[starts[i].num] = str.slice(starts[i].bodyStart, end);
  }
  return objs;
}

const objs = findObjects(s);

function streamData(body) {
  const sm = body.match(/stream\r?\n/);
  if (!sm) return null;
  const start = sm.index + sm[0].length;
  const dict = body.slice(0, sm.index);
  let data;
  const lm = dict.match(/\/Length\s+(\d+)/);
  if (lm) {
    const len = +lm[1];
    data = buf.slice(buf.indexOf(Buffer.from(body.slice(0, 0), 'latin1')) , 0); // placeholder
    // compute absolute offset: find start of stream within file
    // easier: use body substring (latin1 keeps byte-for-byte)
    const raw = Buffer.from(body.slice(start, start + len), 'latin1');
    data = raw;
    if (/FlateDecode/.test(dict)) {
      try { data = zlib.inflateSync(raw); } catch (e) {
        try { data = zlib.inflateRawSync(raw); } catch (e2) { return null; }
      }
    }
    return data;
  }
  const end = body.indexOf('endstream', start);
  if (end < 0) return null;
  let raw = Buffer.from(body.slice(start, end), 'latin1');
  if (/FlateDecode/.test(dict)) {
    try { raw = zlib.inflateSync(raw); } catch (e) { try { raw = zlib.inflateRawSync(raw); } catch (e2) { return null; } }
  }
  return raw;
}

// Build font -> ToUnicode cmap
const fontMaps = {};
for (const num of Object.keys(objs)) {
  const body = objs[num];
  const tu = body.match(/\/ToUnicode\s+(\d+)\s+0\s+R/);
  if (tu) {
    const cmapObj = objs[tu[1]];
    if (!cmapObj) continue;
    const d = streamData(cmapObj);
    if (!d) continue;
    const txt = d.toString('latin1');
    const map = {};
    const bc = txt.match(/beginbfchar([\s\S]*?)endbfchar/g) || [];
    for (const blk of bc) {
      const pairs = blk.match(/<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>/g) || [];
      for (const p of pairs) {
        const mm = p.match(/<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>/);
        const src = parseInt(mm[1], 16);
        let dst = '';
        for (let i = 0; i < mm[2].length; i += 4) dst += String.fromCharCode(parseInt(mm[2].substr(i, 4), 16));
        map[src] = dst;
      }
    }
    const br = txt.match(/beginbfrange([\s\S]*?)endbfrange/g) || [];
    for (const blk of br) {
      const lines = blk.split('\n');
      for (const ln of lines) {
        const mm = ln.match(/<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>/);
        if (mm) {
          const lo = parseInt(mm[1], 16), hi = parseInt(mm[2], 16), dst = parseInt(mm[3], 16);
          for (let c = lo; c <= hi && c - lo < 65536; c++) map[c] = String.fromCharCode(dst + (c - lo));
        }
      }
    }
    // register for all fonts referencing this ToUnicode
    for (const n2 of Object.keys(objs)) {
      if (new RegExp('/ToUnicode\\s+' + tu[1] + '\\s+0\\s+R').test(objs[n2])) {
        // find font resource name(s) is hard; store by object number too
      }
    }
    fontMaps[tu[1]] = map;
  }
}

// Map font resource name -> object number -> cmap
const fontNameToTU = {};
for (const num of Object.keys(objs)) {
  const body = objs[num];
  if (!/\/Type\s*\/Font/.test(body)) continue;
  const tu = body.match(/\/ToUnicode\s+(\d+)\s+0\s+R/);
  let n = null;
  // Resources dict elsewhere maps name->objnum; build reverse later
  fontNameToTU[num] = tu ? fontMaps[tu[1]] : null;
}
// Resources: /Font << /F1 12 0 R >>
const resNameToObj = {};
for (const num of Object.keys(objs)) {
  const body = objs[num];
  const fonts = body.match(/\/Font\s*<<([\s\S]*?)>>/g) || [];
  for (const blk of fonts) {
    const prs = blk.match(/\/([A-Za-z0-9#_.+-]+)\s+(\d+)\s+0\s+R/g) || [];
    for (const p of prs) {
      const mm = p.match(/\/([A-Za-z0-9#_.+-]+)\s+(\d+)\s+0\s+R/);
      resNameToObj[mm[1]] = +mm[2];
    }
  }
}

function decodeString(bytes, cmap) {
  let out = '';
  if (!cmap) return '';
  // 2-byte CIDs
  for (let i = 0; i + 1 < bytes.length; i += 2) {
    const code = (bytes[i] << 8) | bytes[i + 1];
    out += cmap[code] !== undefined ? cmap[code] : '';
  }
  return out;
}

let result = '';
for (const num of Object.keys(objs)) {
  const body = objs[num];
  if (!/stream/.test(body)) continue;
  const d = streamData(body);
  if (!d) continue;
  const txt = d.toString('latin1');
  if (!/\bBT\b/.test(txt) || !/(Tj|TJ)/.test(txt)) continue;
  let curFont = null;
  const tok = /(\/([A-Za-z0-9#_.+-]+)\s+[\d.]+\s+Tf)|(\[([\s\S]*?)\]\s*TJ)|(\((?:\\.|[^\\()])*\)\s*Tj)|(<([0-9A-Fa-f\s]+)>\s*Tj)|(T\*|Td|TD|ET)/g;
  let m;
  while ((m = tok.exec(txt))) {
    if (m[2]) { curFont = resNameToObj[m[2]]; }
    else if (m[4] !== undefined) {
      const cmap = fontNameToTU[curFont];
      const items = m[4].match(/<([0-9A-Fa-f\s]+)>|\((?:\\.|[^\\()])*\)/g) || [];
      for (const it of items) {
        if (it[0] === '<') {
          const hx = it.slice(1, -1).replace(/\s+/g, '');
          const bytes = [];
          for (let i = 0; i + 1 < hx.length; i += 2) bytes.push(parseInt(hx.substr(i, 2), 16));
          result += decodeString(bytes, cmap);
        }
      }
    } else if (m[6]) {
      const hx = m[6].replace(/\s+/g, '');
      const bytes = [];
      for (let i = 0; i + 1 < hx.length; i += 2) bytes.push(parseInt(hx.substr(i, 2), 16));
      result += decodeString(bytes, fontNameToTU[curFont]);
    } else if (m[8]) { result += '\n'; }
  }
  result += '\n--- page break ---\n';
}
fs.writeFileSync(process.argv[3] || (file + '.txt'), result, 'utf8');
console.log('chars:', result.length);
console.log(result.slice(0, 1500));
