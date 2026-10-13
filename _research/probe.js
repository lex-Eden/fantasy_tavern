const fs=require("fs"),zlib=require("zlib");
const f=process.argv[2];
const buf=fs.readFileSync(f);
let s=buf.toString("latin1");
// list objects containing streams
const re=/(\d+)\s+(\d+)\s+obj([\s\S]*?)endobj/g;let m,count=0,inflated=0;
let out=[];
while((m=re.exec(s))){const num=m[1],body=m[3];const sm=body.match(/stream\r?\n/);if(!sm)continue;count++;
 const start=sm.index+sm[0].length;const end=body.indexOf("endstream",start);if(end<0)continue;
 const dict=body.slice(0,sm.index);const raw=Buffer.from(body.slice(start,end),"latin1");
 let data=raw;
 if(/FlateDecode/.test(dict)){try{data=zlib.inflateSync(raw);inflated++;}catch(e){try{data=zlib.inflateRawSync(raw);inflated++;}catch(e2){continue;}}}
 const head=data.slice(0,400).toString("latin1");
 if(/ToUnicode|beginbfchar|beginbfrange/.test(head)||/ToUnicode/.test(dict)){out.push({num,type:"tounicode",len:data.length,txt:data.toString("latin1")});}
 else if(/BT|Tj|TJ/.test(head)){out.push({num,type:"content",len:data.length,txt:data.toString("latin1").slice(0,600)});}
 else {out.push({num,type:"other",len:data.length,txt:head.slice(0,200)});}
}
console.log("objects with streams:",count,"inflated:",inflated,"candidates:",out.length);
for(const o of out.slice(0,12))console.log("---",o.num,o.type,o.len,"\n"+o.txt.slice(0,300).replace(/\n/g,"\\n"));
