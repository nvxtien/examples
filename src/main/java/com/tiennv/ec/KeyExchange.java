package com.tiennv.ec;

/**
 * EC Diffie-Hellman key exchange
 * P is a fixed system parameter
 * k is a secret key
 * Q is a public key
 * Q = kP
 *
 * Alice and Bob have key pairs (ka, Qa) and (kb, Qb)
 * Alice sends Qa = kaP = (xa, ya) to Bob
 * Bob sends Qb = kbP = (xb, yb) to Alice
 * Alice and Bob compute the shared secret:
 * Q = ka(kbP) = kb(kaP) = kakbP
 *
 * Alice encrypts a message Pm and sends the ciphertext Pc to Bob
 * Pc = [Qa, Pm + kaQb)] = [Qa, Pm + kakbP]
 *
 * Bob decrypts Pc
 * (Pm + kakbP) - kbQa = (Pm + kakbP) - kbkaP = Pm
 *
 * Example:
 * Elliptic curve: y^2 = x^3 + x + 4 over p = 23
 * P=P(7, 3), ka = 25, kb = 17
 * Qa = kaP = (4, 16)
 * Qb = kbP = (13, 12)
 * Pm=Pm(10, 8)
 *
 */
public class KeyExchange {

}
