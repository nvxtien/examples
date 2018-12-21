package com.tiennv.ec;

import java.math.BigInteger;

public class Jacobian {


    private BigInteger x, y, z;
    private EllipticCurve curve;

    // Y^2 = Z^3 + aXZ^4 + bZ^6

    public Jacobian(EllipticCurve curve, BigInteger x, BigInteger y, BigInteger z) {
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Jacobian fromAffine(EllipticCurve curve, BigInteger ax, BigInteger ay) {
        this.curve = curve;
        this.x = ax;
        this.y = ay;
        this.z = BigInteger.ONE;
        return this;
    }

    public Point toAffine(EllipticCurve curve) {
        return Point.newPoint(curve, this.x.divide(this.z.pow(2)), this.y.divide(this.z.pow(3)));
    }

    /**
     * https://hyperelliptic.org/EFD/g1p/auto-shortw-jacobian.html#addition-add-2007-bl
     *
     * Z1Z1 = Z1^2
     * Z2Z2 = Z2^2
     * U1 = X1*Z2Z2
     * U2 = X2*Z1Z1
     * S1 = Y1*Z2*Z2Z2
     * S2 = Y2*Z1*Z1Z1
     * H = U2-U1
     * I = (2*H)^2
     * J = H*I
     * r = 2*(S2-S1)
     * V = U1*I
     * X3 = r^2-J-2*V
     * Y3 = r*(V-X3)-2*S1*J
     * Z3 = ((Z1+Z2)^2-Z1Z1-Z2Z2)*H
     *
     */
    public Jacobian add(Jacobian that) {
        BigInteger z1z1 = this.z.pow(2);
        BigInteger z2z2 = that.getZ().pow(2);

        BigInteger u1 = this.x.multiply(z2z2);
        BigInteger u2 = that.x.multiply(z1z1);

        BigInteger s1 = this.y.multiply(that.z).multiply(z2z2);
        BigInteger s2 = that.y.multiply(this.z).multiply(z1z1);

        BigInteger h = u2.subtract(u1);
        BigInteger i = BigInteger.valueOf(2).multiply(h).pow(2);
        BigInteger j = h.multiply(i);

        BigInteger r = BigInteger.valueOf(2).multiply(s2.subtract(s1));
        BigInteger v = u1.multiply(i);

        BigInteger x3 = r.pow(2).subtract(j).subtract(BigInteger.valueOf(2).multiply(v));
        BigInteger y3 = r.multiply(v.subtract(x3)).subtract(BigInteger.valueOf(2).multiply(s1).multiply(j));
        BigInteger z3 = h.multiply(this.z.add(that.z).pow(2).subtract(z1z1).subtract(z2z2));

        return new Jacobian(this.curve, x3, y3, z3);
    }

    /**
     * http://hyperelliptic.org/EFD/g1p/auto-shortw-jacobian-0.html#doubling-dbl-2009-l
     *
     * A = X1^2
     * B = Y1^2
     * C = B^2
     *
     * D = 2*((X1+B)^2-A-C)
     * E = 3*A
     * F = E^2
     * X3 = F-2*D
     * Y3 = E*(D-X3)-8*C
     * Z3 = 2*Y1*Z1
     *
     * @return
     */
    public Jacobian doubling() {
        BigInteger a = this.x.pow(2);
        BigInteger b = this.y.pow(2);
        BigInteger c = b.pow(2);

        BigInteger d = BigInteger.valueOf(2).multiply(this.x.add(b).pow(2).subtract(a).subtract(c));
        BigInteger e = BigInteger.valueOf(3).multiply(a);
        BigInteger f = e.pow(2);

        BigInteger x3 = f.subtract(BigInteger.valueOf(2).multiply(d));
        BigInteger y3 = e.multiply(d.subtract(x3)).subtract(BigInteger.valueOf(8).multiply(c));
        BigInteger z3 = BigInteger.valueOf(2).multiply(this.y).multiply(this.z);
        return new Jacobian(this.curve, x3, y3, z3);
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getZ() {
        return z;
    }
}
