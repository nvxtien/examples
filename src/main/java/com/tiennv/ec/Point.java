package com.tiennv.ec;

import java.math.BigInteger;
import java.util.Objects;

public final class Point {

    public static final Point POINT_INFINITY = new Point();

    private final BigInteger affineX;
    private final BigInteger affineY;

    private Point() {
        this.affineX = null;
        this.affineY = null;
    }

    public Point(BigInteger affineX, BigInteger affineY) {
        assert affineX != null && affineY != null : "invalid parameters";
        this.affineX = affineX;
        this.affineY = affineY;
    }

    public BigInteger getAffineX() {
        return affineX;
    }

    public BigInteger getAffineY() {
        return affineY;
    }

    public boolean isInfinity() {
        return this.affineX ==null && this.affineY ==null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(affineX, point.affineX) &&
                Objects.equals(affineY, point.affineY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(affineX, affineY);
    }

    @Override
    public String toString() {
        return "Point{" +
                "affineX=" + affineX +
                ", affineY=" + affineY +
                '}';
    }
}
