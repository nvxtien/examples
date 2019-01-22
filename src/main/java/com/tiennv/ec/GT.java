package com.tiennv.ec;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Multiplicative group
 * multiplyScalar -> exp
 * add -> multiplyScalar
 * negate -> inverse
 */
public class GT {

    private GFp12 optimalAte;

    public GT(final GFp12 optimalAte) {
        this.optimalAte = optimalAte;
    }

    public GT multiplyScalar(BigInteger k) {
        return new GT(this.getOptimalAte().exp(k));
    }

    public GFp12 getOptimalAte() {
        return optimalAte;
    }

    @Override
    public String toString() {
        return optimalAte.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GT gt = (GT) o;
        return optimalAte.equals(gt.optimalAte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optimalAte);
    }

    public void print() {
        System.out.println(toString());
    }
}
