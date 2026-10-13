import re, sys
src, dst = sys.argv[1], sys.argv[2]
t = open(src, encoding='utf-8').read()
t = t.replace('\n', '')
t = t.replace('《政治》', '\n\n《政治》')
# break before numbered headings like （一） 1. （1）
t = re.sub(r'(?<!^)(（[一二三四五六七八九十]+）)', r'\n\1', t)
t = re.sub(r'(?<!^)(\d+\.\s*)', r'\n\1', t)
t = re.sub(r'(?<!^)(（\d+）)', r'\n\1', t)
t = re.sub(r'([。；])', r'\1\n', t)
t = re.sub(r'\n{2,}', '\n', t)
open(dst, 'w', encoding='utf-8').write(t)
print(len(t))
