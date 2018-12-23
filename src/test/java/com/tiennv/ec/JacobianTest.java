package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class JacobianTest {

    @Test
    public void addTest() {

    }

    @Test
    public void addTwoTheSame() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x = BigInteger.valueOf(4);
        BigInteger y = BigInteger.valueOf(4);
        BigInteger z = BigInteger.ONE;
        Jacobian r = new Jacobian(ellipticCurve, x, y, z);
        System.out.println("p1: " + r.toString());

        Jacobian expected = r.doubling();
        System.out.println("expected: " + expected.toString());

        BigInteger X = expected.getX();
        BigInteger Y = expected.getY();
        BigInteger Z = expected.getZ();

        BigInteger A = Y.modPow(BigInteger.valueOf(2), p);

        BigInteger B = X.pow(3);
        BigInteger C = Z.pow(4).multiply(a).multiply(X); // aXZ^4
        BigInteger D = Z.pow(6).multiply(b);

        BigInteger BCD = B.add(C).add(D).mod(p);
        System.out.println("A: " + A);
        System.out.println("BCD: " + BCD);

        BigInteger invz = Z.modInverse(p);
        BigInteger invz2 = invz.multiply(invz).mod(p);
        BigInteger invz3 = invz.multiply(invz2).mod(p);

        BigInteger xd = X.multiply(invz2).mod(p);
        BigInteger yd = Y.multiply(invz3).mod(p);

        System.out.println("expected: " + xd);
        System.out.println("expected: " + yd);

//        Point aff = expected.toAffine();
//        System.out.println("expected: " + expected.toAffine().toString());

        BigInteger xe = BigInteger.valueOf(2);
        BigInteger ye = BigInteger.valueOf(1);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }
}
