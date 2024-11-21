package com.example.blockchain.models;

import lombok.Getter;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.math.ec.rfc8032.Ed25519;
import java.security.SecureRandom;

public class Ed25519KeyPair {
    private final byte[] privateKey = new byte[Ed25519.SECRET_KEY_SIZE];
    private final byte[] publicKey = new byte[Ed25519.PUBLIC_KEY_SIZE];

    public Ed25519KeyPair() {
        SecureRandom random = new SecureRandom();
        random.nextBytes(privateKey);
        Ed25519.generatePublicKey(privateKey, 0, publicKey, 0);
    }

    public byte[] getPrivateKey() {
        return privateKey.clone(); // Returns a copy of the private key
    }

    public byte[] getPublicKey() {
        return publicKey.clone(); // Return a copy for safety
    }
}

