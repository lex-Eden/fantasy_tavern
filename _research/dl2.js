const https=require("https"),http=require("http"),fs=require("fs");
const urls=JSON.parse(process.argv[2]);
const out=process.argv[3];
function get(u,depth=0){return new Promise((res,rej)=>{if(depth>5)return rej(new Error("redirect"));const lib=u.startsWith("https")?https:http;lib.get(u,{headers:{"User-Agent":"Mozilla/5.0"}},r=>{if(r.statusCode>=300&&r.statusCode<400&&r.headers.location){res(get(new URL(r.headers.location,u).href,depth+1));return;}const c=[];r.on("data",d=>c.push(d));r.on("end",()=>res(Buffer.concat(c)));}).on("error",rej);});}
(async()=>{for(const [k,u] of Object.entries(urls)){try{const b=await get(u);fs.writeFileSync(out+"\\"+k,b);console.log(k,"OK",b.length);}catch(e){console.log(k,"ERR",e.message);}}})();
