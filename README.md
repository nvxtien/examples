# Pairing-based-cryptography

> **Note:** This is an experimental implementation of the Ate optimal pairing in Java. It has never been used in production. Use it at your risk.

## Introduction


## Barreto - Naehrig curves 
A BN curve is an elliptic curve over a finite prime field F<sub>p</sub>, with prime order n and  embedding degree k = 12.

The equation of the curve is 

      ![](https://latex.codecogs.com/gif.latex?E_{u}:%20y^2%20=%20x^3%20+%20b%20\quad%20with%20\quad%20b%20\neq%200)


E<sub>u</sub>: y^2 = x^3 + b\]
\quad The curve order and the characteristic of $\mathbb{F}_p$ are parameterised as:
\[p(u) = 36u^4 + 36u^3 + 24u^2 + 6u + 1\]
\[n(u) = 36u^4 + 36u^3 + 18u^2 + 6u + 1\]
\quad Hence the trace (of Frobenius) of the curve
\[t(u) = 6u^2 + 1\]
\quad Finding b is actually very simple: take the smallest $b \neq 0$ such that $b + 1$ is a
quadratic residue modp and the point $G = (1,\sqrt[2]{b + 1}$ mod $p)$, which is clearly
on the curve. 
