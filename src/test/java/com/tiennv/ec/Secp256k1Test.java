package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Secp256k1Test {

    @Test
    public void PTest() {
        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);
        System.out.println(p.toString(16));

        // p = 2^256 - 2^32 - 2^9 - 2^8 - 2^7 - 2^6 - 2^4 - 1
        BigInteger p4 = new BigInteger("2").pow(4);
        BigInteger p6 = new BigInteger("2").pow(6);
        BigInteger p7 = new BigInteger("2").pow(7);
        BigInteger p8 = p4.pow(2);
        BigInteger p9 = new BigInteger("2").pow(9);
        BigInteger p32 = p8.pow(4);
        BigInteger p256 = p32.pow(8);

        BigInteger pc = p256.subtract(p32).subtract(p9).subtract(p8).subtract(p7).subtract(p6).subtract(p4).subtract(BigInteger.ONE);
        System.out.println(pc.toString(16));
        System.out.println(pc.compareTo(p));

        BigInteger a = BigInteger.valueOf(0);
        BigInteger b = BigInteger.valueOf(7);
        EllipticCurve curve = new EllipticCurve(pc, a, b);

        // The base point G in compressed form
        BigInteger Gx = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16);
        BigInteger Gy = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16);

        Point G = Point.newPoint(curve, Gx, Gy);
        System.out.println("G(x, y) is on the curve: " + G.isOnCurve());

        SecureRandom random = new SecureRandom();

        BigInteger k = new BigInteger(160, random);

        System.out.println("secret key: \n" + k.toString(16));
        System.out.println(k.toString());

        Point R = G.scalarMultiply(k);

        System.out.println("spublic key: \n" + R.toString(16));

        System.out.println("R(x, y) is on the curve: " + R.isOnCurve());


        PrivateKey privateKey = Secp256k1.generateKeyPair(160);
//        privateKey.getPrivateKey();
//        privateKey.getPublicKey();
//        privateKey.getPoint();

        System.out.println(privateKey.toString());


    }
}
