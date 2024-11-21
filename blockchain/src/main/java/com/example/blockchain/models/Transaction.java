package com.example.blockchain.models;

import java.util.List;

public record Transaction(List<Ed25519Signature> extraSignatories) {
}
