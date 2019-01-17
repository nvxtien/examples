# Pairing-based-cryptography

> **Note:** This is an experimental implementation of optimal Ate pairing in Java. It has never been used in production. Use it at your risk.

## Introduction


## Barreto - Naehrig curves 
A BN curve is an elliptic curve over a finite prime field ![][Fp], with prime order n and  embedding degree k = 12.

The equation of the curve is 

![](https://latex.codecogs.com/gif.latex?E_{u}:%20y^2%20=%20x^3%20+%20b%20\quad%20with%20\quad%20b%20\neq%200)

The curve order and the characteristic of ![][Fp] are parameterised as: 

![][pu] 

![][nu] 

Hence the trace (of Frobenius) of the curve
 
![][tu]

Finding b is actually very simple: take the smallest b &ne; 0 such that b + 1 is a quadratic residue modp and the point
 ![](https://latex.codecogs.com/gif.latex?$G%20=%20(1,\sqrt[2]{b%20+%201}$%20mod%20$p)$), which is clearly on the curve.
 [3] 


[Fp]:https://latex.codecogs.com/gif.latex?\mathbb{F}_p
[pu]:https://latex.codecogs.com/gif.latex?$p(u)%20=%2036u^4%20+%2036u^3%20+%2024u^2%20+%206u%20+%201$
[nu]:https://latex.codecogs.com/gif.latex?$n(u)%20=%2036u^4%20+%2036u^3%20+%2018u^2%20+%206u%20+%201$
[tu]:https://latex.codecogs.com/gif.latex?$t(u)%20=%206u^2%20+%201$
