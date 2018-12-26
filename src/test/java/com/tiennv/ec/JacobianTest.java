package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JacobianTest {

    @Test
    public void addDoubling() {
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

    @Test
    public void addDoublingAnotherCurve() {
        BigInteger p = BigInteger.valueOf(17);
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(7);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x = BigInteger.valueOf(1);
        BigInteger y = BigInteger.valueOf(3);
        BigInteger z = BigInteger.ONE;
        Jacobian r = new Jacobian(ellipticCurve, x, y, z);
        System.out.println("Jacobian point: " + r.toString());

        System.out.println("====================================");
        Jacobian expected = r.doubling();
        System.out.println("doubling: " + expected.toString());

        Point point = expected.toAffine();
        System.out.println("point: " + point.toString());

        BigInteger xe = BigInteger.valueOf(6);
        BigInteger ye = BigInteger.valueOf(5);

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

    @Test
    public void addTest() {
        BigInteger p = BigInteger.valueOf(17);
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(7);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x1 = BigInteger.valueOf(2);
        BigInteger y1 = BigInteger.valueOf(0);
        BigInteger z1 = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, x1, y1, z1);

        BigInteger x2 = BigInteger.valueOf(1);
        BigInteger y2 = BigInteger.valueOf(3);
        BigInteger z2 = BigInteger.ONE;
        Jacobian r2 = new Jacobian(ellipticCurve, x2, y2, z2);

        Jacobian r = r1.add(r2);
        Point expected = r.toAffine();
        assertTrue(expected.isOnCurve());

        BigInteger x = BigInteger.valueOf(6);
        BigInteger y = BigInteger.valueOf(12);

        Point actual = Point.newPoint(ellipticCurve, x, y);
        assertEquals(expected, actual);

    }

    @Test
    public void multiplyTest() {
        BigInteger p = BigInteger.valueOf(23);
        BigInteger a = BigInteger.valueOf(9);
        BigInteger b = BigInteger.valueOf(17);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x1 = BigInteger.valueOf(16);
        BigInteger y1 = BigInteger.valueOf(5);
        BigInteger z1 = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, x1, y1, z1);

        Jacobian r = r1.scalarMultiply(BigInteger.valueOf(9));
        System.out.println(r.toString());
        Point expected = r.toAffine();
        System.out.println(expected.toString());
        assertTrue(expected.isOnCurve());

        BigInteger x = BigInteger.valueOf(4);
        BigInteger y = BigInteger.valueOf(5);

        Point actual = Point.newPoint(ellipticCurve, x, y);
        assertEquals(expected, actual);
    }

    /**
     * http://www.site.uottawa.ca/~chouinar/Handout_Elliptic_Curve_Crypto.pdf
     */
    @Test
    public void multiplyTest751() {
        BigInteger p = BigInteger.valueOf(751);
        BigInteger a = BigInteger.valueOf(-1);
        BigInteger b = BigInteger.valueOf(188);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x1 = BigInteger.valueOf(0);
        BigInteger y1 = BigInteger.valueOf(376);
        BigInteger z1 = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, x1, y1, z1);

        Jacobian r = r1.scalarMultiply(BigInteger.valueOf(2));
        System.out.println(r.toString());
        Point expected = r.toAffine();
        System.out.println(expected.toString());

        assertTrue(expected.isInfinity());
    }

    @Test
    public void multiplyTest751G2() {
        BigInteger p = BigInteger.valueOf(751);
        BigInteger a = BigInteger.valueOf(-1);
        BigInteger b = BigInteger.valueOf(188);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x1 = BigInteger.valueOf(0);
        BigInteger y1 = BigInteger.valueOf(376);
        BigInteger z1 = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, x1, y1, z1);

        Jacobian r = r1.scalarMultiply(BigInteger.valueOf(2));
        System.out.println(r.toString());
        Point expected = r.toAffine();
        System.out.println(expected.toString());

        BigInteger x = BigInteger.valueOf(1);
        BigInteger y = BigInteger.valueOf(376);

        Point actual = Point.newPoint(ellipticCurve, x, y);
        assertEquals(expected, actual);
    }

    @Test
    public void multiplyTest751G3() {
        BigInteger p = BigInteger.valueOf(751);
        BigInteger a = BigInteger.valueOf(-1);
        BigInteger b = BigInteger.valueOf(188);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x1 = BigInteger.valueOf(0);
        BigInteger y1 = BigInteger.valueOf(376);
        BigInteger z1 = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, x1, y1, z1);

        Jacobian r = r1.scalarMultiply(BigInteger.valueOf(3));
        System.out.println(r.toString());
        Point expected = r.toAffine();
        System.out.println(expected.toString());

        BigInteger x = BigInteger.valueOf(750);
        BigInteger y = BigInteger.valueOf(375);

        Point actual = Point.newPoint(ellipticCurve, x, y);
        assertEquals(expected, actual);
    }

    @Test
    public void multiplyTest751G765() {
        BigInteger p = BigInteger.valueOf(751);
        BigInteger a = BigInteger.valueOf(-1);
        BigInteger b = BigInteger.valueOf(188);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x1 = BigInteger.valueOf(0);
        BigInteger y1 = BigInteger.valueOf(376);
        BigInteger z1 = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, x1, y1, z1);

        Jacobian r = r1.scalarMultiply(BigInteger.valueOf(765));
        System.out.println(r.toString());
        Point expected = r.toAffine();
        System.out.println(expected.toString());

        BigInteger x = BigInteger.valueOf(417);
        BigInteger y = BigInteger.valueOf(320);

        Point actual = Point.newPoint(ellipticCurve, x, y);
        assertEquals(expected, actual);
    }

    @Test
    public void multiplyTest751G85() {
        BigInteger p = BigInteger.valueOf(751);
        BigInteger a = BigInteger.valueOf(-1);
        BigInteger b = BigInteger.valueOf(188);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x1 = BigInteger.valueOf(0);
        BigInteger y1 = BigInteger.valueOf(376);
        BigInteger z1 = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, x1, y1, z1);

        Jacobian r = r1.scalarMultiply(BigInteger.valueOf(85));
        System.out.println(r.toString());
        Point expected = r.toAffine();
        System.out.println(expected.toString());

        BigInteger x = BigInteger.valueOf(671);
        BigInteger y = BigInteger.valueOf(558);

        Point actual = Point.newPoint(ellipticCurve, x, y);
        assertEquals(expected, actual);
    }

    @Test
    public void multiplyTest751G113() {
        BigInteger p = BigInteger.valueOf(751);
        BigInteger a = BigInteger.valueOf(-1);
        BigInteger b = BigInteger.valueOf(188);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger x1 = BigInteger.valueOf(0);
        BigInteger y1 = BigInteger.valueOf(376);
        BigInteger z1 = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, x1, y1, z1);

        Jacobian r = r1.scalarMultiply(BigInteger.valueOf(85));
        System.out.println(r.toString());
        Point expected = r.toAffine();
        System.out.println(expected.toString());

        BigInteger x = BigInteger.valueOf(00);
        BigInteger y = BigInteger.valueOf(000);

        Point actual = Point.newPoint(ellipticCurve, x, y);
        assertEquals(expected, actual);
    }
}
