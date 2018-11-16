package com.tiennv.ec;

import java.math.BigInteger;
import java.util.Objects;

public final class Point {

    private final BigInteger x;
    private final BigInteger y;


    public Point(BigInteger x, BigInteger y) {
        assert x == null && y == null || x != null && y != null : "invalid parameters";
        this.x = x;
        this.y = y;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public boolean isInfinity() {
        return this.x==null && this.y==null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(x, point.x) &&
                Objects.equals(y, point.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
