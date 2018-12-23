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
        System.out.println("Jacobian point: " + r.toString());
        /*
        Jacobian expected = r.doubling();
        System.out.println("expected: " + expected.toString());
        Point point = expected.toAffine();
        System.out.println("point: " + point.toString());

        BigInteger invz = Z.modInverse(p);
        BigInteger invz2 = invz.multiply(invz).mod(p);
        BigInteger invz3 = invz.multiply(invz2).mod(p);

        BigInteger xd = X.multiply(invz2).mod(p);
        BigInteger yd = Y.multiply(invz3).mod(p);

        System.out.println("expected: " + xd);
        System.out.println("expected: " + yd);*/

        System.out.println("====================================");
        Jacobian expected = r.doubling();
        System.out.println("doubling: " + expected.toString());

        Point point = expected.toAffine();
        System.out.println("point: " + point.toString());

        BigInteger xe = BigInteger.valueOf(2);
        BigInteger ye = BigInteger.valueOf(1);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(point, actual);

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
        assertEquals(A, BCD);
        System.out.println("Y^2 = X^3 + aXZ^4 + bZ^6 is true");

        System.out.println("==================================== doubling2");
        Jacobian expected2 = r.doubling2();
        System.out.println("expected2: " + expected2.toString());
        X = expected2.getX();
        Y = expected2.getY();
        Z = expected2.getZ();

        A = Y.modPow(BigInteger.valueOf(2), p);

        B = X.pow(3);
        C = Z.pow(4).multiply(a).multiply(X); // aXZ^4
        D = Z.pow(6).multiply(b);

        BCD = B.add(C).add(D).mod(p);
        System.out.println("A: " + A);
        System.out.println("BCD: " + BCD);

//        Point point2 = expected2.toAffine();
//        System.out.println("point2: " + point2.toString());
    }
}
