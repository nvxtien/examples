package com.tiennv.ec;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MuSignTest {

    @Test
    public void gen() {

        PrivateKey privateKey = Secp256k1.generateKeyPair(160);
        MuSig muSig = new MuSig(privateKey);

        PrivateKey privateKey1 = Secp256k1.generateKeyPair(160);
        MuSig cosigner = new MuSig(privateKey1);

        List<Point> pubKeys = new ArrayList<>();
        pubKeys.add(privateKey1.getPoint());
        muSig.getCosigners(pubKeys);

        TCommitment tCommitment = muSig.sendTComm();

        cosigner.receiveTComm(tCommitment);
        TCommitment tCommitment1 = cosigner.sendTComm();

        muSig.receiveTComm(tCommitment1);
        RCommitment rCommitment = muSig.sendRComm();

        cosigner.receiveRComm(rCommitment);
        RCommitment rCommitment1 = cosigner.sendRComm();

        muSig.receiveRComm(rCommitment1);

        muSig.sign();
    }
}
