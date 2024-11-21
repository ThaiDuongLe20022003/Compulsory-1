package com.example.blockchain.models;

import org.bouncycastle.math.ec.rfc8032.Ed25519;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class ClassTest {

    @Test
    void test() {
        Ed25519KeyPair keyPair = new Ed25519KeyPair();

        String msg = "Hello, World!";
        String context = "MyAppDomain";
        Ed25519Signature ed25519Signature = new Ed25519Signature(keyPair.getPrivateKey(), msg.getBytes(), context);

        // Create Datum
        Datum datum = new Datum(new Random().nextLong(), ed25519Signature);

        // Create Redeemer
        Redeemer redeemer = new Redeemer(12345L, msg.getBytes());

        // Create OutputReference (placeholder)
        OutputReference placeholderUtxo = new OutputReference("", 0);

        // Create Transaction with extra signatories
        List<Ed25519Signature> signatories = List.of(ed25519Signature);
        Transaction transaction = new Transaction(signatories);

        boolean isValid = TransactionValidator.verify(
                keyPair.getPublicKey(),
                transaction.extraSignatories(),
                datum.getOwner(),
                redeemer.getMsg(),
                context.getBytes()
        );
        assertEquals("should be true", true, isValid);
    }
}

