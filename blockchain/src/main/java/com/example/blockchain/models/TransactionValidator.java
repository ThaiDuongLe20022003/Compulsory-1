package com.example.blockchain.models;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519phSigner;

import java.util.List;

@Slf4j
public class TransactionValidator {

    public static boolean verify(byte[] publicKey, List<Ed25519Signature> transactionSignatures, Ed25519Signature ownerSignature, byte[] msg, byte[] context) {
        try {
            // Initialize the public key parameters
            Ed25519PublicKeyParameters publicKeyParams = new Ed25519PublicKeyParameters(publicKey, 0);

            // Initialize the Ed25519 signer for verification
            Ed25519phSigner signer = new Ed25519phSigner(context);
            signer.init(false, publicKeyParams); // false = verification mode

            // Feed the message bytes to the signer
            signer.update(msg, 0, msg.length);

            // Verify the signature
            boolean isValidSignature = false;

            for (Ed25519Signature ed25519Signature : transactionSignatures) {
                isValidSignature = signer.verifySignature(ed25519Signature.getSignatureByte());
                if (isValidSignature) {
                    break;
                }
            }

            boolean isOwnerSignatureValid = transactionSignatures.contains(ownerSignature);
            return isValidSignature && isOwnerSignatureValid;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false; // Return false if any exception occurs
        }
    }
}
