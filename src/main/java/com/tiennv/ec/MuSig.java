package com.tiennv.ec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * https://eprint.iacr.org/2018/068.pdf
 *
 * Signing:
 * L = {Xi} multiset of all public keys
 * ai = Hagg(L,Xi) the signer computes with its public key Xi
 */
public class MuSig {

    private RCommitment rCommitment;
    private PrivateKey privateKey;

    private List<TCommitment> tCommitments = new ArrayList<>();
    private List<RCommitment> rCommitments = new ArrayList<>();
    private List<Point> cosigners = new ArrayList<>();

    private Point aggregatedPubKeys;
    private BigInteger rnonce;
    private byte[] ai;
    private BigInteger s;

    private byte[] multisetL;

    private BigInteger signatures;

    public MuSig(PrivateKey privateKey) {
        this.privateKey = privateKey;
        this.cosigners.add(privateKey.getPoint());
    }

    private byte[] aggH(final byte[] L, final Point pub) {
        return hash(concat(L, pub.toString().getBytes()));
    }

    private Point computeXa(final byte[] L, final Point pub) {
        this.ai = aggH(L, pub);
        return pub.scalarMultiply(new BigInteger(ai));
    }

    private Point computeAggPubKeys(List<Point> pubKeys) {
        byte[] L = multisetOfPublicKeys(pubKeys);
        Optional<Point> optPubKeys = pubKeys.stream().map(pub -> computeXa(L, pub)).reduce((x, y) -> x.add(y));
        Point aggregatedPubKeys = optPubKeys.get();
        return aggregatedPubKeys;
    }


    /*private byte[] aggH(final byte[] L, final Point pub) {
        return hash(concat(L, pub.toString().getBytes()));
    }*/

    /*private Point computeXa(final byte[] L, final Point pub) {
        byte[] a = aggH(L, pub);
        return pub.scalarMultiply(new BigInteger(a));
    }*/

    /*public Signature sign(List<Point> pubKeys, Point pubKey, List<Commitment> commitments, BigInteger r, byte[] a, PrivateKey priv, byte[] m) throws InvalidCommitment {
        byte[] L = multisetOfPublicKeys(pubKeys);

        Optional<Point> optPubKeys = pubKeys.stream().map(point -> computeXa(L, pubKey)).reduce((x, y) -> x.add(y));
        Point aggregatedPubKeys = optPubKeys.get();

        for (int i=0; i<commitments.size(); i++) {
            if (Arrays.equals(commitments.get(i).getT(), commitments.get(i).getR().toString().getBytes())) {
                throw new InvalidCommitment();
            }
        }

        Optional<Point> optR = commitments.stream().map(Commitment::getR).reduce((x, y) -> x.add(y));
        Point sumR = optR.get();

        byte[] XR = concat(aggregatedPubKeys.toString().getBytes(), sumR.toString().getBytes());

        byte[] XRm = concat(XR, m);

        byte[] c = hash(XRm);

        BigInteger s = r.add(new BigInteger(c).multiply(new BigInteger(a)).multiply(priv.getPrivateKey())).mod(Secp256k1.n);
        return null;
    }*/

    public byte[] sigH(byte[] input) {
        return  hash(input);
    }

    private byte[] comH(Point pR) {
        return hash(pR.toString().getBytes());
    }

    private byte[] multisetOfPublicKeys(List<Point> pubKeys) {
        return pubKeys.stream().map(Point::toString).collect(Collectors.joining()).getBytes();
    }

    private void multiset(Point pubKey) {
        multisetL = concat(multisetL, pubKey.toString().getBytes());
    }

    public byte[] hash(byte[] input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest.digest(input);
    }

    private TCommitment computeTComm() {
        SecureRandom random = new SecureRandom();
        rnonce = new BigInteger(160, random);
        Point pR = Secp256k1.G.scalarMultiply(rnonce);
        byte[] t = comH(pR);
        this.rCommitment = new RCommitment(this.privateKey.getPoint(), pR);
        return new TCommitment(this.privateKey.getPoint(), t);
    }

    private Point Xa(byte[] L, Point X) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write( L );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write( X.toString().getBytes() );
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] LX = outputStream.toByteArray();
        System.out.println(LX);

        byte[] a = hash(LX);

        return X.scalarMultiply(new BigInteger(a));
    }


    private byte[] concat(final byte[] a, final byte[] b) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write( a );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write( b );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public TCommitment sendTComm() {
        TCommitment tCommitment = computeTComm();
        // if it is a cosigner, ignoring this statement
        tCommitments.add(tCommitment);

        return tCommitment;
    }

    public void receiveTComm(TCommitment tCommitment) {
        tCommitments.add(tCommitment);
    }

    public RCommitment sendRComm() {
        // Upon reception of commitments t2,...,tn from other cosigners, it sends R1
        RCommitment rCommitment = this.rCommitment;
        rCommitments.add(rCommitment);
        return rCommitment;
    }

    public void receiveRComm(RCommitment rCommitment) {
        // Upon reception of R2,...,Rn from other cosigners, it checks that ti = Hcom(Ri)
        // for all i ∈{2,...,n} and aborts the protocol if this is not the case
        Point pR = rCommitment.getR();
        List<TCommitment> tcomm = this.tCommitments.stream().filter(x -> x.getPublicKey().equals(pR)).collect(Collectors.toList());
        if (tcomm.size() == 1) {
            byte[] rh =  comH(pR);
            if (Arrays.equals(tcomm.get(0).getT(), rh)) {
                this.rCommitments.add(rCommitment);
            }
        }
    }

    public void getCosigners(List<Point> pubKeys) {
        this.cosigners.addAll(pubKeys);
        this.aggregatedPubKeys = computeAggPubKeys(cosigners);
    }

    public BigInteger sign(byte[] m) {
        Optional<Point> optR = this.rCommitments.stream().map(RCommitment::getR).reduce((x, y) -> x.add(y));
        Point aggR = optR.get();

        byte[] XR = concat(aggregatedPubKeys.toString().getBytes(), aggR.toString().getBytes());

        byte[] XRm = concat(XR, m);

        byte[] c = sigH(XRm);

        BigInteger s = rnonce.add(new BigInteger(c).multiply(new BigInteger(ai)).multiply(this.privateKey.getPrivateKey())).mod(Secp256k1.n);
        this.s = s;
        return s;
    }

    public BigInteger cosign(BigInteger signature) {
        signatures = s.add(signature).mod(Secp256k1.n);
    }
}
