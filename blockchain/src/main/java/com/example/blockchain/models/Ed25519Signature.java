package com.example.blockchain.models;

import lombok.Getter;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519phSigner;

import java.nio.charset.StandardCharsets;

@Getter
public class Ed25519Signature {
    private final byte[] signatureByte;
    public Ed25519Signature(byte[] privateKey, byte[] msg, String context) {
        this.signatureByte = this.sign(privateKey, msg, context);
    }
    private byte[] sign(byte[] privateKey, byte[] msg, String context) {
        Ed25519phSigner signer = new Ed25519phSigner(context.getBytes(StandardCharsets.UTF_8));
        Ed25519PrivateKeyParameters ed25519PrivateKeyParameters = new Ed25519PrivateKeyParameters(privateKey);
        signer.init(true, ed25519PrivateKeyParameters);
        signer.update(msg, 0, msg.length);
        return signer.generateSignature();
    }
}
