package com.tiennv.ec;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Secp256k1 {

    public static final BigInteger P = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);
    public static final BigInteger A = BigInteger.valueOf(0);
    public static final BigInteger B = BigInteger.valueOf(7);

    // The base point G in uncompressed form
    public static final BigInteger Gx = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16);
    public static final BigInteger Gy = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16);

    public static final EllipticCurve Secp256k1 = new EllipticCurve(P, A, B);
    public static final Point G = Point.newPoint(Secp256k1, Gx, Gy);

    private Secp256k1() {
    }

    public static PrivateKey generateKeyPair(int numBits) {
        SecureRandom random = new SecureRandom();
        BigInteger k = new BigInteger(numBits, random);
        Point r = G.scalarMultiply(k);

        return new PrivateKey(k, r);
    }
}
