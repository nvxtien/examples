package com.tiennv.ec;

import java.math.BigInteger;

public class Arithmetic {

    /**
     * The Cipolla-Lehmer square root algorithm
     * Input: a prime p where p > 2, a quadratic residue c in GF(p) and an integer b where 0 < b < p
     * Output: y where CL(c, b, p) = y. The output y will be 0 or a square root of c in GF(p).
     *
     * (1) h := (b^2−4c)^((p−1)/2) (mod p)
     * (2) if h ≡ 1 (mod p) or if h ≡ 0 (mod p) then s := 0
     * (3) if h ≡ −1 (mod p) then s := 1
     * (4) q(x) := x^((p+1)/2) mod〈x^2−bx+c〉 where q(x) = c1x+c0 for integers c0, c1
     * (5) y := sc0
     * (6) Return y as the output
     *
     * @return
     */
    /*public static BigInteger modSquareCL(BigInteger c, BigInteger b, BigInteger p) {
        *//**
         * Given a non-zero n and an odd prime p, Euler's criterion tells us that n has a square root
         * (n is a quadratic residue) if and only if: n^((p−1)/2) ≡ 1 (mod p).
         *
         * In contrast, if a number z has no square root (is a non-residue), Euler's criterion tells us that:
         * z^((p−1)/2) ≡ − 1 (mod p).
         *//*
        if (isQuadraticResidue(c, p)) {
            return true;
        }
        return false;
    }*/

    private static boolean isQuadraticResidue(BigInteger n, BigInteger p) {
        return n.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), p).compareTo(BigInteger.ONE) == 0;
    }
}