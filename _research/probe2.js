const fs = require('fs'), zlib = require('zlib');
const buf = fs.readFileSync(process.argv[2]);
const s = buf.toString('latin1');
const re = /(\d+)\s+(\d+)\s+obj\b/g; let m; const objs = {};
while ((m = re.exec(s))) { const st = re.lastIndex; const en = s.indexOf('endobj', st); if (en > 0) objs[+m[1]] = s.slice(st, en); }
function streamData(body) {
  const sm = body.match(/stream\r?\n/); if (!sm) return null;
  const start = sm.index + sm[0].length; const dict = body.slice(0, sm.index);
  const lm = dict.match(/\/Length\s+(\d+)/);
  let raw;
  if (lm) raw = Buffer.from(body.slice(start, start + +lm[1]), 'latin1');
  else { const e = body.indexOf('endstream', start); raw = Buffer.from(body.slice(start, e), 'latin1'); }
  if (/FlateDecode/.test(dict)) { try { return zlib.inflateSync(raw); } catch (e) { try { return zlib.inflateRawSync(raw); } catch (e2) { return null; } } }
  return raw;
}
let shown = 0;
for (const num of Object.keys(objs)) {
  const d = streamData(objs[num]);
  if (!d) continue;
  const t = d.toString('latin1');
  if (!/beginbf/.test(t)) continue;
  shown++;
  console.log('=== obj', num, 'len', d.length);
  console.log(t.slice(0, 900));
  if (shown >= 2) break;
}
console.log('font objs:', Object.keys(objs).filter(n => /\/Type\s*\/Font/.test(objs[n])).length);
for (const n of Object.keys(objs)) if (/\/Type\s*\/Font/.test(objs[n])) console.log('font', n, objs[n].slice(0, 400).replace(/\n/g, ' '));
