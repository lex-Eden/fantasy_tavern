import urllib.request, ssl, sys
ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE
urls = {
 "zz_2024orig.pdf": "https://file.hebeea.edu.cn/files/article/2024/08/20240821082621_194.pdf",
 "zz_2026rev.pdf":  "https://file.hebeea.edu.cn/files/article/2025/12/20251226093908_630.pdf",
}
base = sys.argv[1]
for name, u in urls.items():
    try:
        req = urllib.request.Request(u, headers={"User-Agent":"Mozilla/5.0"})
        data = urllib.request.urlopen(req, timeout=90, context=ctx).read()
        open(base + "\\" + name, "wb").write(data)
        print(name, "OK", len(data))
    except Exception as e:
        print(name, "FAIL", type(e).__name__, e)
