package com.tiennv.ec;

public class Pairings {
    public static GT pair(final G1 g1, final G2 g2) {
        OptimalAtePairing pairing = new OptimalAtePairing();
        return new GT(pairing.optimalAte(g2.getTwistPoint(), g1.getCurvePoint()));
    }
}
