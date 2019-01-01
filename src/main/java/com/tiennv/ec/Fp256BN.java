package com.tiennv.ec;

import java.math.BigInteger;

/**
 * Fp256BN implements a particular bilinear group at the 128-bit security level.
 *
 * The curve equation is E : y^2 = x^3 + 3
 * The prime p is given by the BN polynomial parametrization
 * p = 36u^4 + 36u^3 + 24u^2 + 6u + 1, where u = v^3 and v = 1868033.
 * n = 36u^4 + 36u^3 + 18u^2 + 6u + 1
 *
 * n optimal ate pairing over Barreto-Naehrig curves
 * https://cryptojedi.org/papers/dclxvi-20100714.pdf
 *
 * BN128 is Barreto-Naehrig pairing friendly curve providing 128 bits of security defined as: y^2=x^3+b
 * over the prime field 𝔽q where:
 * q = 21888242871839275222246405745257275088696311157297823662689037894645226208583
 * r = 21888242871839275222246405745257275088548364400416034343698204186575808495617
 * b = 3
 *
 * This curve choice admits a pairing construction over the two groups 𝔾1 and 𝔾2 where
 * 𝔾1 is a Barreto-Naehrig curve over 𝔽q
 * 𝔾2 is a the subgroup of order r in the sextic twist of 𝔾2 over 𝔽q2 with equation y^2=x^3+b/ζ
 * 𝔾T is the subgroup of the rth roots of unity in 𝔽q12
 *
 * With the bilinear pairing e with:
 * e:𝔾1×𝔾2→𝔽q12
 *
 */
public class Fp256BN {
    public static final BigInteger p = new BigInteger("65000549695646603732796438742359905742825358107623003571877145026864184071783", 10);
    public static final BigInteger a = new BigInteger("0", 10);
    public static final BigInteger b = new BigInteger("7", 10);
}
