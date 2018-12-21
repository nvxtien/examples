package com.tiennv.ec;

import java.math.BigInteger;

public class G2 {

    private final EllipticCurve curve;
    private final BigInteger n;

    private GFp2 x, y;

    public G2(final EllipticCurve curve, final BigInteger n) {
        this.curve = curve;
        this.n = n;
    }

    public G2 newPoint(final GFp2 x, final GFp2 y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
