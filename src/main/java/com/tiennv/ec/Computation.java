package com.tiennv.ec;

public interface Computation {
    Point inverse(Point r);
    Point add(Point p1, Point p2);
    Point doubling(Point r);
}
