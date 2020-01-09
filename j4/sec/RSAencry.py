p = 13
q = 2
e = 5
c = p * q
eCode = []
dCode = []
eCode.append("IKKI")
eCode.append("TAKESHINOCHOUSENJYOU")
dCode.append("OVELUNGER")
dCode.append("XAGAHAOHCMECDCNNSFSUGENDCMA")

d = 1
while d*e%((p-1)*(q-1)) != 1:
   d+=1

print("公開鍵:c = " + str(c) + ", e = " + str(e))
print("秘密鍵:c = " + str(c) + ", d = " + str(d))

i = 0
s = ""
for sC in eCode[0]:
   i = ord(sC) - 64
   s += chr(i ** e % c + 64)
print(eCode[0] + " (平文)\n=> " + s + " (暗号化文)")

s = ""
for sC in eCode[1]:
   i = ord(sC) - 64
   s += chr(i ** e % c + 64)
print(eCode[1] + " (平文)\n=> " + s + " (暗号化文)")

s = ""
for dC in dCode[0]:
   i = ord(dC) - 64
   s += chr(i ** d % c + 64)
print(dCode[0] + " (暗号化文)\n=> " + s + " (平文)")

s = ""
for dC in dCode[1]:
   i = ord(dC) - 64
   s += chr(i ** d % c + 64)
print(dCode[1] + " (暗号化文)\n=> " + s + " (平文)")
