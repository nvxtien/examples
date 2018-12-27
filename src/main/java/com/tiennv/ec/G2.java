package com.tiennv.ec;

import java.math.BigInteger;

public class G2 {

    private final EllipticCurve curve;
    private final BigInteger n;

    private GFp2 a, b;

    public G2(final EllipticCurve curve, final BigInteger n) {
        this.curve = curve;
        this.n = n;
    }

    public G2 newPoint(final GFp2 a, final GFp2 b) {
        this.a = a;
        this.b = b;
        return this;
    }

    public G2 doubling() {

        return newPoint(a, b);
    }
}
