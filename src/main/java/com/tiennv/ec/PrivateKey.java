package com.tiennv.ec;

import java.math.BigInteger;

public class PrivateKey {

    private BigInteger privateKey;
    private Point r;

    public PrivateKey(BigInteger priv, Point r) {
        this.privateKey = priv;
        this.r = r;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return r.getAffineX();
    }

    public Point getPoint() {
        return r;
    }

    @Override
    public String toString() {
        return "PrivateKey{" +
                "privateKey=" + privateKey +
                ", R=" + "Point{" +
                "X=" + r.getAffineX().toString() +
                ", Y=" + r.getAffineX().toString() +
                '}' +
                '}';
    }
}
