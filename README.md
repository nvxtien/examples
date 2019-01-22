# Pairing-based-cryptography

> **Note:** This is an experimental implementation of optimal Ate pairing in Java. It has never been used in production. Use it at your risk.

## Introduction

## Introduction to Parings

An admissible bilinear pairing is a function ![](https://latex.codecogs.com/gif.latex?$e:%20\mathbb{G}_1%20\times%20\mathbb{G}_2%20\rightarrow%20\mathbb{G}_T%20$), 
where ![][g1] and ![][g2] are cyclic subgroups of elliptic curve groups, ![][gt] is a cyclic subgroup of 
the multiplicative group of a finite field, ![][g1], ![][g2] and ![][gt] have order r, and the following conditions hold:

• Bilinearity: for ![](https://latex.codecogs.com/gif.latex?$P,%20Q%20\in%20\mathbb{G}_1$) and ![](https://latex.codecogs.com/gif.latex?$R,%20S%20\in%20\mathbb{G}_2$), 
![](https://latex.codecogs.com/gif.latex?$e(P%20+%20Q,%20R)%20=%20e(P,%20R).e(Q,%20R)$) and ![](https://latex.codecogs.com/gif.latex?$e(P,R%20+%20S)%20=%20e(P,R).e(P,S)$)

• Non-degeneracy: ![](https://latex.codecogs.com/gif.latex?$e(P,%20R)%20\neq%201$) for some ![](https://latex.codecogs.com/gif.latex?$P%20\in%20\mathbb{G}_1$) 
and ![](https://latex.codecogs.com/gif.latex?$R%20\in%20\mathbb{G}_2$). 
Or, equivalently, ![](https://latex.codecogs.com/gif.latex?$e(P,%20R)%20=%201$) for all ![](https://latex.codecogs.com/gif.latex?$R%20\in%20\mathbb{G}_2$) 
if and only if ![](https://latex.codecogs.com/gif.latex?$P%20=%20\mathcal{O}$); and ![](https://latex.codecogs.com/gif.latex?$e(P,%20R)%20=%201$) 
for all ![](https://latex.codecogs.com/gif.latex?$P%20\in%20\mathbb{G}_1$) if and only if ![](https://latex.codecogs.com/gif.latex?$P%20\in%20\mathbb{G}_1$)

Also, it immediately follows that ![](https://latex.codecogs.com/gif.latex?$e(aP,%20bR)=e(P,%20R)^{ab}=e(bP,%20aR)$) for any two integers a and b.

## Barreto - Naehrig curves 
A BN curve is an elliptic curve over a finite prime field ![][Fp], with prime order n and  embedding degree k = 12.

The equation of the curve is 

![](https://latex.codecogs.com/gif.latex?E_{u}:%20y^2%20=%20x^3%20+%20b%20\quad%20with%20\quad%20b%20\neq%200)

The curve order and the characteristic of ![][Fp] are parameterised as: 

![][pu] 

![][nu] 

Hence the trace (of Frobenius) of the curve
 
![][tu]

with ![](https://latex.codecogs.com/gif.latex?$u%20\in%20\mathbb{Z}$)

Finding b is actually very simple: take the smallest b &ne; 0 such that b + 1 is a quadratic residue modp and the point
 ![](https://latex.codecogs.com/gif.latex?$G%20=%20(1,\sqrt[2]{b%20+%201}$%20\quad%20mod%20\quad%20$p)$), which is clearly on the curve.
 [3] 


[Fp]:https://latex.codecogs.com/gif.latex?\mathbb{F}_p
[pu]:https://latex.codecogs.com/gif.latex?$p(u)%20=%2036u^4%20+%2036u^3%20+%2024u^2%20+%206u%20+%201$
[nu]:https://latex.codecogs.com/gif.latex?$n(u)%20=%2036u^4%20+%2036u^3%20+%2018u^2%20+%206u%20+%201$
[tu]:https://latex.codecogs.com/gif.latex?$t(u)%20=%206u^2%20+%201$

[pairing]:https://latex.codecogs.com/gif.latex?$e:%20\mathbb{G}_1%20\times%20\mathbb{G}_2%20\rightarrow%20\mathbb{G}_T%20$
[g1]:https://latex.codecogs.com/gif.latex?$\mathbb{G}_1$
[g2]:https://latex.codecogs.com/gif.latex?$\mathbb{G}_2$
[gt]:https://latex.codecogs.com/gif.latex?$\mathbb{G}_T$