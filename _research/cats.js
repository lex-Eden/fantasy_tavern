const fs = require('fs');
const flat = fs.readFileSync(process.argv[2], 'utf8').replace(/\s+/g, '');
// Table row pattern: ...招生专业代码 招生专业名称 专业门类 联考专业 招生计划数 授予学位 学制 学费...
// Build regex: (专业门类)(联考专业)(\d+)(学位)(学制)
const re = /(经济学|管理学1|管理学2|理工1|理工2|理工3|理工4|理工5|农学1|农学2|农学3|农学4|教育学1|教育学2|教育学3|文学\d+|法学1|法学2|历史学|医学\d+|艺术\d+)((?:[\u4e00-\u9fa5A-Za-z0-9()（）\-]+?\/)*[\u4e00-\u9fa5A-Za-z0-9()（）\-]+?)(\d{1,4})(工学|理学|管理学|经济学|文学|法学|教育学|医学|艺术学|农学|历史学)(\d)(\d{4,6})/g;
const map = {};
let m;
while ((m = re.exec(flat))) {
  const cat = m[1], group = m[2];
  if (!map[cat]) map[cat] = {};
  map[cat][group] = (map[cat][group] || 0) + 1;
}
const order = ['经济学','管理学1','管理学2','理工1','理工2','理工3','理工4','理工5','农学1','农学2','农学3','农学4','教育学1','教育学2','教育学3','历史学','法学1','法学2'];
const keys = Object.keys(map).sort((a, b) => {
  const ia = order.indexOf(a), ib = order.indexOf(b);
  return (ia < 0 ? 99 : ia) - (ib < 0 ? 99 : ib) || a.localeCompare(b);
});
for (const k of keys) {
  const groups = Object.entries(map[k]).sort((a, b) => b[1] - a[1]);
  console.log('== ' + k + ' (' + groups.length + ' distinct 联考专业串)');
  for (const [g, c] of groups.slice(0, 3)) console.log('   [' + c + '] ' + g);
}
