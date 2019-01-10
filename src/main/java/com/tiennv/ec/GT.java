package com.tiennv.ec;

public class GT {

    private final G1 g1;
    private final G2 g2;

    private GFp12 x, y;

    public GT(final G1 g1, final G2 g2) {
        this.g1 = g1;
        this.g2 = g2;
    }

    public static GFp12 pair(final G1 g1, final G2 g2) {
        OptimalAtePairing pairing = new OptimalAtePairing();
        return pairing.optimalAte(g2.getTwistPoint(), g1.getCurvePoint());
    }
}
