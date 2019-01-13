package com.tiennv.ec;

import java.math.BigInteger;

/**
 * Multiplicative group
 * multiply -> exp
 * add -> multiply
 * negate -> inverse
 */
public class GT {

    private GFp12 optimalAte;

    public GT(final GFp12 optimalAte) {
        this.optimalAte = optimalAte;
    }

    public static GT pair(final G1 g1, final G2 g2) {
        OptimalAtePairing pairing = new OptimalAtePairing();
        return new GT(pairing.optimalAte(g2.getTwistPoint(), g1.getCurvePoint()));
    }

    public GT multiplyScalar(BigInteger k) {
        return new GT(this.getgOptimalAte().exp(k));
    }

    public GFp12 getgOptimalAte() {
        return optimalAte;
    }

    @Override
    public String toString() {
        return "GT{" +
                "gFp12=" + optimalAte.toString() +
                '}';
    }

    public void print() {
        System.out.println(toString());
    }
}
