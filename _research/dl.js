const https=require("https"),fs=require("fs");
const urls={
 gaoshu1:"https://file.hebeea.edu.cn/files/article/2025/12/20251229100212_698.pdf",
 xxjs:"https://file.hebeea.edu.cn/files/article/2025/12/20251229095452_53.pdf",
 duibiao2026:"https://file.hebeea.edu.cn/upload/resources/file/2026/02/13/26507.pdf",
 jihua2026:"https://file.hebeea.edu.cn/upload/resources/file/2026/02/13/26506.pdf"
};
const out=process.argv[2];
(async()=>{for(const [k,u] of Object.entries(urls)){try{const buf=await new Promise((res,rej)=>{https.get(u,{headers:{"User-Agent":"Mozilla/5.0"}},r=>{if(r.statusCode>=300&&r.statusCode<400&&r.headers.location){https.get(r.headers.location,{headers:{"User-Agent":"Mozilla/5.0"}},r2=>{const c=[];r2.on("data",d=>c.push(d));r2.on("end",()=>res(Buffer.concat(c)))});return;}const c=[];r.on("data",d=>c.push(d));r.on("end",()=>res(Buffer.concat(c)));}).on("error",rej);});fs.writeFileSync(out+"\\"+k+".pdf",buf);console.log(k,"OK",buf.length);}catch(e){console.log(k,"ERR",e.message);}}})();
