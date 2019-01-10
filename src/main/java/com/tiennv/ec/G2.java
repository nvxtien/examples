package com.tiennv.ec;

import java.math.BigInteger;

public class G2 {

    private TwistPoint twistPoint;

    public G2(final TwistPoint p) {
        this.twistPoint = p;
    }

    public G2 add(final G2 a, final G2 b) {
        return new G2(a.getTwistPoint().add(b.getTwistPoint()));
    }

    public G2 multiply(final BigInteger k) {
        return new G2(this.twistPoint.scalarMultiply(k));
    }

    public TwistPoint getTwistPoint() {
        return twistPoint;
    }
}
