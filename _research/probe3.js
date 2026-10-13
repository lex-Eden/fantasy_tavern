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
const fontObjs = Object.keys(objs).filter(n => /\/Type\s*\/Font/.test(objs[n]));
console.log('total objs', Object.keys(objs).length, 'font objs', fontObjs.length);
for (const n of fontObjs.slice(0, 5)) console.log('FONT', n, objs[n].replace(/\s+/g, ' ').slice(0, 300));
// resource dicts
const resObjs = Object.keys(objs).filter(n => /\/Font\s*<</.test(objs[n]));
console.log('res objs', resObjs.length);
for (const n of resObjs.slice(0, 3)) { const mm = objs[n].match(/\/Font\s*<<([\s\S]*?)>>/); console.log('RES', n, mm && mm[1].replace(/\s+/g, ' ').slice(0, 400)); }
// pages
const pageObjs = Object.keys(objs).filter(n => /\/Type\s*\/Page[^s]/.test(objs[n]));
console.log('pages', pageObjs.length);
console.log('PAGE0', objs[pageObjs[0]] ? objs[pageObjs[0]].replace(/\s+/g, ' ').slice(0, 400) : 'n/a');
