# Pairing-based-cryptography
## Introduction
This is an experimental implementation of the Ate optimal pairing in Java. It has never been used in production. Use it at your risk.

## Barreto - Naehrig curve 
A BN curve is an elliptic curve over a finite prime field F<sub>p</sub>, with prime order n and  embedding degree k = 12.  
The equation of the curve is

![](https://latex.codecogs.com/gif.latex?\mathbb{E}_u:%20y^2%20=%20x^3%20+%20b "dsfsdfdfdf")

The curve order and the characteristic of $\mathbb{F}_p$ are parameterised as:

![](https://latex.codecogs.com/gif.latex?p(u)%20=%2036u^4%20+%2036u^3%20+%2024u^2%20+%206u%20+%201)

[n(u) = 36u^4 + 36u^3 + 18u^2 + 6u + 1\]

Hence the trace (of Frobenius) of the curve

[t(u) = 6u^2 + 1\]

Finding b is actually very simple: take the smallest $b \neq 0$ such that $b + 1$ is a
quadratic residue modp and the point $G = (1,\sqrt[2]{b + 1}$ mod $p)$, which is clearly
on the curve. 
