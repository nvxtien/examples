package com.tiennv.ec;

import java.math.BigInteger;

public class Jacobian {
    private BigInteger x, y, z;

    public Jacobian fromAffine(BigInteger ax, BigInteger ay) {
        this.x = ax;
        this.y = ay;
        this.z = BigInteger.ONE;
        return this;
    }

    public Point toAffine(EllipticCurve curve) {
        return Point.newPoint(curve, this.x.divide(this.z.pow(2)), this.y.divide(this.z.pow(3)));
    }
}
