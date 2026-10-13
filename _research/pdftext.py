# Minimal pure-python PDF text extractor (FlateDecode + ToUnicode CMap aware)
import re, sys, zlib

data = open(sys.argv[1], 'rb').read()

# ---- collect indirect objects -------------------------------------------------
objs = {}
for m in re.finditer(rb'(\d+)\s+(\d+)\s+obj', data):
    num = int(m.group(1))
    start = m.end()
    end = data.find(b'endobj', start)
    if end == -1:
        continue
    objs[num] = data[start:end]

def get_stream(body):
    m = re.search(rb'stream\r?\n', body)
    if not m:
        return None
    s = m.end()
    e = body.find(b'endstream', s)
    raw = body[s:e]
    if raw.endswith(b'\r\n'):
        raw = raw[:-2]
    elif raw.endswith(b'\n') or raw.endswith(b'\r'):
        raw = raw[:-1]
    if b'FlateDecode' in body[:m.start()]:
        try:
            raw = zlib.decompress(raw)
        except Exception:
            try:
                raw = zlib.decompressobj().decompress(raw)
            except Exception:
                return None
    return raw

def dict_of(body):
    m = re.search(rb'<<', body)
    if not m:
        return body
    i = m.start(); depth = 0
    while i < len(body):
        if body[i:i+2] == b'<<':
            depth += 1; i += 2; continue
        if body[i:i+2] == b'>>':
            depth -= 1; i += 2
            if depth == 0:
                return body[m.start():i]
            continue
        i += 1
    return body[m.start():]

# ---- parse ToUnicode CMaps ---------------------------------------------------
def parse_cmap(txt):
    mp = {}
    for blk in re.findall(rb'beginbfchar(.*?)endbfchar', txt, re.S):
        for a, b in re.findall(rb'<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>', blk):
            src = int(a, 16)
            dst = bytes.fromhex(b.decode())
            try:
                mp[src] = dst.decode('utf-16-be', 'ignore')
            except Exception:
                pass
    for blk in re.findall(rb'beginbfrange(.*?)endbfrange', txt, re.S):
        for a, b, c in re.findall(rb'<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>\s*<([0-9A-Fa-f]+)>', blk):
            lo, hi = int(a, 16), int(b, 16)
            base = bytes.fromhex(c.decode())
            n = int.from_bytes(base[-2:], 'big')
            for k in range(hi - lo + 1):
                bb = base[:-2] + (n + k).to_bytes(2, 'big')
                try:
                    mp[lo + k] = bb.decode('utf-16-be', 'ignore')
                except Exception:
                    pass
    return mp

fontmaps = {}   # object number of font -> cmap
fontnames = {}  # resource name -> object number
for num, body in objs.items():
    if b'/ToUnicode' in body:
        m = re.search(rb'/ToUnicode\s+(\d+)\s+\d+\s+R', body)
        if m:
            tu = int(m.group(1))
            if tu in objs:
                st = get_stream(objs[tu])
                if st:
                    fontmaps[num] = parse_cmap(st)

# map /F1 -> obj, scanning font resource dicts
for num, body in objs.items():
    for nm, on in re.findall(rb'/([A-Za-z0-9#+._-]+)\s+(\d+)\s+\d+\s+R', body):
        try:
            key = nm.decode()
        except Exception:
            continue
        if on and int(on) in fontmaps and key not in fontnames:
            fontnames[key] = int(on)

def decode_hex(h, cmap):
    out = []
    h = h.strip()
    if len(h) % 4 == 0:
        step = 4
    elif len(h) % 2 == 0:
        step = 2
    else:
        return ''
    for i in range(0, len(h), step):
        code = int(h[i:i+step], 16)
        if cmap and code in cmap:
            out.append(cmap[code])
        elif step == 2:
            try:
                out.append(bytes([code]).decode('latin-1'))
            except Exception:
                pass
    return ''.join(out)

def unescape_lit(s):
    res = bytearray(); i = 0
    while i < len(s):
        c = s[i]
        if c == 0x5c and i + 1 < len(s):
            n = s[i+1]
            mp = {0x6e: 10, 0x72: 13, 0x74: 9, 0x62: 8, 0x66: 12,
                  0x28: 40, 0x29: 41, 0x5c: 92}
            if n in mp:
                res.append(mp[n]); i += 2; continue
            if 0x30 <= n <= 0x37:
                j = i + 1; oct_ = ''
                while j < len(s) and len(oct_) < 3 and 0x30 <= s[j] <= 0x37:
                    oct_ += chr(s[j]); j += 1
                res.append(int(oct_, 8) & 0xFF); i = j; continue
            i += 2; continue
        res.append(c); i += 1
    return bytes(res)

# ---- extract text from content streams ---------------------------------------
pages = []
for num, body in objs.items():
    if b'/Type' in body and re.search(rb'/Type\s*/Page[^s]', body):
        pages.append((num, body))
if not pages:
    pages = [(n, b) for n, b in objs.items() if b'BT' in b and b'Tf' in b]

def content_for(body):
    out = b''
    if re.search(rb'/Contents\s+\d+\s+\d+\s+R', body):
        for on in re.findall(rb'/Contents\s+(\d+)\s+\d+\s+R', body):
            st = get_stream(objs.get(int(on), b''))
            if st:
                out += st + b'\n'
    else:
        st = get_stream(body)
        if st:
            out += st
    return out

alltext = []
for num, body in pages:
    c = content_for(body)
    if not c:
        continue
    cur = None
    buf = []
    for tok in re.finditer(rb'/([A-Za-z0-9#+._-]+)\s+[\d.]+\s+Tf|\[(.*?)\]\s*TJ|\((?:\\.|[^\\()])*\)\s*Tj|<([0-9A-Fa-f\s]+)>\s*Tj|(T\*|Td|TD|ET)', c, re.S):
        if tok.group(1):
            nm = tok.group(1).decode('latin-1')
            cur = fontmaps.get(fontnames.get(nm, -1))
        elif tok.group(2) is not None:
            cm = cur
            for h in re.findall(rb'<([0-9A-Fa-f\s]+)>', tok.group(2)):
                buf.append(decode_hex(h.decode(), cm))
            for l in re.findall(rb'\((?:\\.|[^\\()])*\)', tok.group(2)):
                buf.append(unescape_lit(l[1:-1]).decode('latin-1'))
        elif tok.group(3) is not None:
            buf.append(decode_hex(tok.group(3).decode(), cur))
        elif tok.group(0).startswith(b'T') and not tok.group(0).startswith(b'Tf'):
            buf.append('\n')
        else:
            m = re.match(rb'\((.*)\)\s*Tj', tok.group(0), re.S)
            if m:
                buf.append(unescape_lit(m.group(1)).decode('latin-1'))
    alltext.append(''.join(buf))

txt = '\n'.join(alltext)
txt = re.sub(r'\n{3,}', '\n\n', txt)
open(sys.argv[2], 'w', encoding='utf-8').write(txt)
print('pages:', len(pages), 'chars:', len(txt))
print('fontmaps:', len(fontmaps), 'fontnames:', list(fontnames.items())[:10])
