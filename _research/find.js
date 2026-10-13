const fs = require('fs');
const t = fs.readFileSync(process.argv[2], 'utf8');
const flat = t.replace(/\s+/g, '');
const targets = process.argv.slice(3);
for (const tg of targets) {
  const idxs = [];
  let i = -1;
  while ((i = flat.indexOf(tg, i + 1)) >= 0) idxs.push(i);
  console.log('### ' + tg + ' : ' + idxs.length + ' hits');
  const seen = new Set();
  let shown = 0;
  for (const p of idxs) {
    const ctx = flat.slice(Math.max(0, p - 60), p + 120);
    if (seen.has(ctx.slice(30, 90))) continue;
    seen.add(ctx.slice(30, 90));
    console.log('   ...' + ctx + '...');
    if (++shown >= 6) break;
  }
}
