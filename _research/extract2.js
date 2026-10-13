const fs = require('fs'), zlib = require('zlib');
const buf = fs.readFileSync(process.argv[2]);
const s = buf.toString('latin1');
const re = /(\d+)\s+(\d+)\s+obj\b/g; let m; const objs = {};
while ((m = re.exec(s))) { const st = re.lastIndex; const en = s.indexOf('endobj', st); if (en > 0) objs[+m[1]] = s.slice(st, en); }
function sd(body) {
  const sm = body.match(/stream\r?\n/); if (!sm) return null;
  const start = sm.index + sm[0].length; const dict = body.slice(0, sm.index);
  const lm = dict.match(/\/Length\s+(\d+)/);
  let raw;
  if (lm) raw = Buffer.from(body.slice(start, start + +lm[1]), 'latin1');
  else { const e = body.indexOf('endstream', start); if (e < 0) return null; raw = Buffer.from(body.slice(start, e), 'latin1'); }
  if (/FlateDecode/.test(dict)) { try { return zlib.inflateSync(raw); } catch (e) { try { return zlib.inflateRawSync(raw); } catch (e2) { return null; } } }
  return raw;
}
const cmapCache = {};
function getCmap(objNum) {
  if (cmapCache[objNum] !== undefined) return cmapCache[objNum];
  const o = objs[objNum];
  if (!o) return (cmapCache[objNum] = null);
  const d = sd(o); if (!d) return (cmapCache[objNum] = null);
  const t = d.toString('latin1'); const map = {};
  for (const blk of (t.match(/beginbfchar([\s\S]*?)endbfchar/g) || [])) {
    for (const p of (blk.match(/<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>/g) || [])) {
      const mm = p.match(/<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>/);
      let dst = ''; for (let i = 0; i + 3 < mm[2].length + 1; i += 4) dst += String.fromCharCode(parseInt(mm[2].substr(i, 4), 16));
      map[parseInt(mm[1], 16)] = dst;
    }
  }
  for (const blk of (t.match(/beginbfrange([\s\S]*?)endbfrange/g) || [])) {
    const arrRe = /<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>\s*\[([\s\S]*?)\]/g; let am;
    while ((am = arrRe.exec(blk))) {
      const lo = parseInt(am[1], 16); const items = am[3].match(/<([0-9A-Fa-f]*)>/g) || [];
      items.forEach((it, i) => { const h = it.replace(/[<>]/g, ''); let dst = ''; for (let k = 0; k + 3 < h.length + 1; k += 4) dst += String.fromCharCode(parseInt(h.substr(k, 4), 16)); map[lo + i] = dst; });
    }
    const lnRe = /<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>/g; let lm2;
    while ((lm2 = lnRe.exec(blk))) {
      const lo = parseInt(lm2[1], 16), hi = parseInt(lm2[2], 16), dv = parseInt(lm2[3], 16);
      for (let c = lo; c <= hi; c++) map[c] = String.fromCharCode(dv + (c - lo));
    }
  }
  return (cmapCache[objNum] = map);
}
function hexToStr(h, cmap) {
  const bytes = [];
  for (let i = 0; i + 1 < h.length; i += 2) bytes.push(parseInt(h.substr(i, 2), 16));
  let out = '';
  for (let i = 0; i + 1 < bytes.length; i += 2) { const c = (bytes[i] << 8) | bytes[i + 1]; out += (cmap && cmap[c] !== undefined) ? cmap[c] : ''; }
  return out;
}
let result = '';
let pages = 0, stats = { tj: 0, unmapped: 0 };
for (const num of Object.keys(objs)) {
  const body = objs[num];
  if (!/\/Type\s*\/Page[^s]/.test(body)) continue;
  pages++;
  const fm = body.match(/\/Font\s*<<([\s\S]*?)>>/);
  const name2cmap = {};
  if (fm) {
    for (const p of (fm[1].match(/\/([A-Za-z0-9#_.+-]+)\s+(\d+)\s+0\s+R/g) || [])) {
      const mm = p.match(/\/([A-Za-z0-9#_.+-]+)\s+(\d+)\s+0\s+R/);
      const fo = objs[+mm[2]]; if (!fo) continue;
      const tu = fo.match(/\/ToUnicode\s+(\d+)\s+0\s+R/);
      if (tu) name2cmap[mm[1]] = getCmap(+tu[1]);
    }
  }
  const cont = body.match(/\/Contents\s+(\d+)\s+0\s+R/);
  if (!cont) continue;
  const cd = sd(objs[+cont[1]]); if (!cd) continue;
  const txt = cd.toString('latin1');
  let cur = null;
  const tok = /\/([A-Za-z0-9#_.+-]+)\s+[\d.]+\s+Tf|\[([\s\S]*?)\]\s*TJ|(\((?:\\.|[^\\()])*\))\s*Tj|<([0-9A-Fa-f\s]+)>\s*Tj|(T\*|Td|TD|ET|Tm)/g;
  let mm2;
  while ((mm2 = tok.exec(txt))) {
    if (mm2[1]) cur = mm2[1];
    else if (mm2[2] !== undefined) {
      const cmap = name2cmap[cur];
      for (const it of (mm2[2].match(/<([0-9A-Fa-f\s]*)>|\((?:\\.|[^\\()])*\)/g) || [])) {
        if (it[0] === '<') { const h = it.slice(1, -1).replace(/\s+/g, ''); const r = hexToStr(h, cmap); if (!r && h.length >= 4) stats.unmapped++; result += r; }
      }
      stats.tj++;
    } else if (mm2[4]) { const h = mm2[4].replace(/\s+/g, ''); const r = hexToStr(h, name2cmap[cur]); if (!r && h.length >= 4) stats.unmapped++; result += r; stats.tj++; }
    else if (mm2[5]) result += '\n';
  }
  result += '\n===PAGE===\n';
}
fs.writeFileSync(process.argv[3], result, 'utf8');
console.log('pages', pages, JSON.stringify(stats), 'chars', result.length);
