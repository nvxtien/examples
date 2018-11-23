package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PointTest {

    @Test
    public void NAFTest() {
        Utils.NAF(new BigInteger("1"));
        Utils.NAF(new BigInteger("2"));
        Utils.NAF(new BigInteger("3"));
        Utils.NAF(new BigInteger("4"));
        Utils.NAF(new BigInteger("5"));
        Utils.NAF(new BigInteger("6"));
        Utils.NAF(new BigInteger("7"));

        System.out.println(Utils.NAF(new BigInteger("7")).get(3));

        Utils.NAF(new BigInteger("8"));
        Utils.NAF(new BigInteger("9"));

        Utils.NAF(new BigInteger("14"));
    }


    @Test
    public void naf9() {
        BigInteger p = BigInteger.valueOf(23);
        BigInteger a = BigInteger.valueOf(9);
        BigInteger b = BigInteger.valueOf(17);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(16);
        BigInteger yr = BigInteger.valueOf(5);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("p1: " + r.toString());

        Point expected = r.scalarMultiply(BigInteger.valueOf(9));
        System.out.println("expected: " + expected);

        BigInteger xe = BigInteger.valueOf(4);
        BigInteger ye = BigInteger.valueOf(5);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }

    @Test
    public void naf6() {
        BigInteger p = BigInteger.valueOf(23);
        BigInteger a = BigInteger.valueOf(9);
        BigInteger b = BigInteger.valueOf(17);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(16);
        BigInteger yr = BigInteger.valueOf(5);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("p1: " + r.toString());

        Point expected = r.scalarMultiply(BigInteger.valueOf(6));
        System.out.println("expected: " + expected);

        BigInteger xe = BigInteger.valueOf(7);
        BigInteger ye = BigInteger.valueOf(3);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }

    @Test
    public void naf7() {
        BigInteger p = BigInteger.valueOf(23);
        BigInteger a = BigInteger.valueOf(9);
        BigInteger b = BigInteger.valueOf(17);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(16);
        BigInteger yr = BigInteger.valueOf(5);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("p1: " + r.toString());

        Point expected = r.scalarMultiply(BigInteger.valueOf(7));
        System.out.println("expected: " + expected);

        BigInteger xe = BigInteger.valueOf(8);
        BigInteger ye = BigInteger.valueOf(7);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }

    @Test
    public void naf314() {
        BigInteger p = BigInteger.valueOf(23);
        BigInteger a = BigInteger.valueOf(9);
        BigInteger b = BigInteger.valueOf(17);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(16);
        BigInteger yr = BigInteger.valueOf(5);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("p1: " + r.toString());

        Point expected = r.scalarMultiply(BigInteger.valueOf(314));
        System.out.println("expected: " + expected);

        BigInteger xe = BigInteger.valueOf(7);
        BigInteger ye = BigInteger.valueOf(20);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }

    @Test
    public void isOnCurve() {
        BigInteger p = BigInteger.valueOf(31);
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(13);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(9);
        BigInteger yr = BigInteger.valueOf(10);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("p1: " + r.toString());

        boolean expected = r.isOnCurve();
        System.out.println("expected: " + expected);

        assertTrue(expected);
    }

    @Test
    public void KeyExchange() {
        BigInteger p = BigInteger.valueOf(23);
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xp = BigInteger.valueOf(7);
        BigInteger yp = BigInteger.valueOf(3);
        Point P = Point.newPoint(ellipticCurve, xp, yp);
        System.out.println("P: " + P.toString());

        BigInteger ka = BigInteger.valueOf(25);
        BigInteger kb = BigInteger.valueOf(17);

        Point Qa = P.scalarMultiply(ka);
        System.out.println("Qa: " + Qa.toString() + Qa.isOnCurve());

        System.out.println("==================================================");

        Point Qb = P.scalarMultiply(kb);
        System.out.println("Qb: " + Qb.toString() + Qb.isOnCurve());


        BigInteger xm = BigInteger.valueOf(10);
        BigInteger ym = BigInteger.valueOf(18);
        Point Pm = Point.newPoint(ellipticCurve, xm, ym);
        System.out.println("Pm: " + Pm.toString() + Pm.isOnCurve());

        Point S = Qb.scalarMultiply(ka);
        System.out.println("S: " + S.toString() + S.isOnCurve());

        EFp eFp = new EFp();

        Point Pc = eFp.add(Pm, Qb.scalarMultiply(ka));
        System.out.println("Pc: " + Pc.toString());

        Point SS = Qa.scalarMultiply(kb);
        System.out.println("SS: " + SS.toString() + S.isOnCurve());

        Point expected = eFp.add(Pc, eFp.inverse(Qa.scalarMultiply(kb)));
        System.out.println("expected: " + expected.toString());

        boolean onCurve = expected.isOnCurve();
        System.out.println("onCurve: " + onCurve);

        assertTrue(expected.equals(Pm));
    }
}