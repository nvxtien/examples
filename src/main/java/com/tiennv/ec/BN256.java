package com.tiennv.ec;

/**
 * BN256 implements a particular bilinear group at the 128-bit security level.
 *
 * The curve equation is E : y^2 = x^3 + 3
 * The prime p is given by the BN polynomial parametrization
 * p = 36u^4 + 36u^3 + 24u^2 + 6u + 1, where u = v^3 and v = 1868033.
 *
 * n optimal ate pairing over Barreto-Naehrig curves
 * https://cryptojedi.org/papers/dclxvi-20100714.pdf
 */
public class BN256 {
}
