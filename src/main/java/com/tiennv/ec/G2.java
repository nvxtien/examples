package com.tiennv.ec;

import java.math.BigInteger;

public class G2 {

    private final EllipticCurve curve  = new EllipticCurve(Fp256BN.p, Fp256BN.a, Fp256BN.b);

    private GFp2 x, y, z;
    private Jacobian r;

    public G2(final GFp2 x, final GFp2 y, final GFp2 z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /*public G2 add(final G2 g) {
        Jacobian s = new Jacobian(curve, g.getX(), g.getY(), g.getZ());
        Jacobian t = r.add(s);
        return new G2(t.getX(), t.getY(), t.getZ());
    }

    public G2 subtract(final G2 g) {
        return this.add(g.negate());
    }

    public G2 doubling() {
        Jacobian t = r.doubling();
        return new G2(t.getX(), t.getY(), t.getZ());
    }

    public G2 multiply(final BigInteger k) {
        Jacobian t = r.scalarMultiply(k);
        return new G2(t.getX(), t.getY(), t.getZ());
    }*/

    public G2 negate() {
        return new G2(this.getX(), this.getY().negate(), this.getZ());
    }

    public GFp2 getX() {
        return x;
    }

    public GFp2 getY() {
        return y;
    }

    public GFp2 getZ() {
        return z;
    }
}
