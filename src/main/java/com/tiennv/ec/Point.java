package com.tiennv.ec;

import java.math.BigInteger;
import java.util.Objects;

public final class Point {

    public static final Point POINT_INFINITY = new Point();

    private final BigInteger affineX;
    private final BigInteger affineY;

    private final EllipticCurve ec;

//    private final Point point;

    private Point() {
        this.ec = null;
        this.affineX = null;
        this.affineY = null;
    }

    private Point(EllipticCurve ec, BigInteger affineX, BigInteger affineY) {
        assert ec != null && affineX != null && affineY != null : "invalid parameters";
        this.ec = ec;
        this.affineX = affineX;
        this.affineY = affineY;
    }

    public static Point newPoint(EllipticCurve ec, BigInteger affineX, BigInteger affineY) {
        return new Point(ec, affineX, affineY);
    }

    public Point add(Point r) {
        EFp eFp = new EFp();
        return eFp.add(this, r);
    }

    public Point scalarMultiply(BigInteger k) {
        Computation computation = new NAF();
        return computation.scalarMultiply(k, this);
    }

    public EllipticCurve getEC() {
        return ec;
    }

    public BigInteger getAffineX() {
        return affineX;
    }

    public BigInteger getAffineY() {
        return affineY;
    }

    public boolean isOnCurve() {

        assert !this.isInfinity() : "The point must be on the elliptic curve";

        BigInteger fx = this.getAffineX().mod(ec.getP());
        BigInteger fa = ec.getA().mod(ec.getP());
        BigInteger fb = ec.getB().mod(ec.getP());
        BigInteger right = fx.pow(3).add(fx.multiply(fa)).add(fb).mod(ec.getP());

        BigInteger fy = this.getAffineY().mod(ec.getP());
        BigInteger left = fy.modPow(BigInteger.valueOf(2), ec.getP());

        return right.compareTo(left) == 0;
    }

    public boolean isInfinity() {
        return this.affineX ==null && this.affineY ==null;
    }

    @Override
    public String toString() {
        return toString(10);
    }

    public String toString(int radix) {
        return "Point{" +
                "affineX=" + affineX.toString(radix) +
                ", affineY=" + affineY.toString(radix) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(affineX, point.affineX) &&
                Objects.equals(affineY, point.affineY) &&
                Objects.equals(ec, point.ec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(affineX, affineY, ec);
    }
}
