package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

/**
 * We create an example tripartite Diffie-Helman key exchange.
 */
public class TripartiteDHTest {

    @Test
    public void tripartiteTest() {


        G1 g1 = new G1(CurvePoint.GENERATOR);
        G2 g2 = new G2(TwistPoint.GENERATOR);

        GT test = GT.pair(g1, g2);
        test = test.multiplyScalar(Fp256BN.n);
        test.print();

        BigInteger a = new BigInteger("2");
        BigInteger b = new BigInteger("199999");
        BigInteger c = new BigInteger("199999");

        G1 pa = G1.multiplyBaseScalar(a);
        pa.print();

        G2 qa = G2.multiplyBaseScalar(a);
        qa.print();

//        G1{x=GFp{4062534355977912733299777421397494108926584881726437723242321564179011504485}, y=GFp{3046900766983434549974833066048120581694938661294828292431741173134258628365}, z=GFp{1}}
//        bn256.G1(4062534355977912733299777421397494108926584881726437723242321564179011504485, 3046900766983434549974833066048120581694938661294828292431741173134258628365)


//      G2{x=GFp2{x=59927578126862636086832814129077742734260689452696771010895412296143667746211, y=54952930061809850496692595958158609574339126978755622048249382632547867406485}, y=GFp2{x=16688816548089249544807710431443648737000867162658939349813935924644263586693, y=63452769682808524161861388928032618183185319239119072364816295620689227591133}, z=GFp2{x=0, y=1}}
//        bn256.G2((59927578126862636086832814129077742734260689452696771010895412296143667746211,   54952930061809850496692595958158609574339126978755622048249382632547867406485),         (16688816548089249544807710431443648737000867162658939349813935924644263586693,   63452769682808524161861388928032618183185319239119072364816295620689227591133), (0,1))


        // bn256.G2((21167961636542580255011770066570541300993051739349375019639421053990175267184,   64746500191241794695844075326670126197795977525365406531717464316923369116492),         (20666913350058776956210519119118544732556678129809273996262322366050359951122,   17778617556404439934652658462602675281523610326338642107814333856843981424549), (0,1))
      // G2{x=GFp2{x=21167961636542580255011770066570541300993051739349375019639421053990175267184, y=64746500191241794695844075326670126197795977525365406531717464316923369116492}, y=GFp2{x=20666913350058776956210519119118544732556678129809273996262322366050359951122, y=17778617556404439934652658462602675281523610326338642107814333856843981424549}, z=GFp2{x=0, y=1}}
        /*GT test = GT.pair(pa, qa);
        test = test.multiplyScalar(Fp256BN.n);
        test.print();*/

        G1 pb = G1.multiplyBaseScalar(b);
//        pb.print();
        G2 qb = G2.multiplyBaseScalar(b);
//        qb.print();

        G1 pc = G1.multiplyBaseScalar(c);
//        pb.print();
        G2 qc = G2.multiplyBaseScalar(c);
//        qb.print();

        /*GT gta = GT.pair(pb, qc);
        gta = gta.multiplyScalar(a);
        gta.print();

        GT gtb = GT.pair(pc, qa);
        gtb = gtb.multiplyScalar(b);
        gtb.print();

        GT gtc = GT.pair(pa, qb);
        gtc = gtc.multiplyScalar(c);
        gtc.print();*/

//        GT{gFp12=
//        GFp12{x=GFp6{
//                      x=GFp2{x=57073769120875232160537685178853087767677524035541875320569117256045787099826, y=55855578800437413416493082914307264023118307658929947122482674032749252189928},
//                      y=GFp2{x=56537772493264565167421702609170836973789962048768539976645155903359757263859, y=62842614981500950693139801021209833410835400236603302640231053156538562706901},
//                      z=GFp2{x=57725205168992739932932875086639152989210454230647132796115466095472865754783, y=10856493298933950978538289152825265318872434833458213136584786451382313629040}},
//              y=GFp6{
//                      x=GFp2{x=51580560198237945316354317163764090097098801162747625335428216572743650592074, y=14568256263996298770485941462832481617005980222172497356593151169108152438111},
//                      y=GFp2{x=64987105372077533671882436512116259903945479533960410094802492078766927124142, y=40013423596311075925448794541330533606492408623725035965373462942356778782692},
//                      z=GFp2{x=54864127390759608983690760038911654812893257985123499676932962882294739871380, y=59755163281118885880197693935866829594785859440606764544657965678440511689955}}}}
    }
}
