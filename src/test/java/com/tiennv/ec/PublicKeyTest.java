package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PublicKeyTest {
    @Test
    public void publicKeyTest() {
        PrivateKey privateKey = Secp256k1.generateKeyPair(256);
        PublicKey publicKey = privateKey.getPublicKey();

        System.out.println("=============================================");
        BigInteger x = new BigInteger("3699dfdf73462601422ed1e1309bb624e04c6b86fcdc51618c0af69f26ec4132", 16);
        System.out.println(x.toString(16).length());
        System.out.println(x.toString().length());
        System.out.println(x.bitCount()/8);
        System.out.println("=============================================");

        System.out.println("=============================================");
        x = new BigInteger("97ddae0f3a25b92268175400149d65d6887b9cefaf28ea2c078e05cdc15a3c0a", 16);
        System.out.println(x.toString(16).length());
        System.out.println(x.toString().length());
        System.out.println(x.bitCount()/4);
        System.out.println("=============================================");

        Point q = publicKey.getPoint();
        assertFalse(q.isInfinity());
        assertTrue(q.scalarMultiply(Secp256k1.n).isInfinity());
        assertTrue(Secp256k1.G.scalarMultiply(Secp256k1.n).isInfinity());

        System.out.println(q.scalarMultiply(Secp256k1.n.subtract(BigInteger.ONE)));
        System.out.println(q);

        System.out.println(q.scalarMultiply(Secp256k1.n.subtract(BigInteger.ONE)).getAffineY().and(BigInteger.ONE));
        System.out.println(q.getAffineY().and(BigInteger.ONE));

        Point pointNminusOne = Secp256k1.G.scalarMultiply(Secp256k1.n.subtract(BigInteger.ONE));
        System.out.println(pointNminusOne);
        System.out.println(Secp256k1.G);

        System.out.println(pointNminusOne.getAffineY().toString(16));
        System.out.println(Secp256k1.G.getAffineY().toString(16));

        System.out.println(pointNminusOne.getAffineY().pow(2).mod(Secp256k1.P));
        System.out.println(Secp256k1.G.getAffineY().pow(2).mod(Secp256k1.P));

        System.out.println(pointNminusOne.add(Secp256k1.G));

        System.out.println(Secp256k1.G.scalarMultiply(Secp256k1.n));

        System.out.println(pointNminusOne.inverse());
        System.out.println(Secp256k1.G);
    }
}
