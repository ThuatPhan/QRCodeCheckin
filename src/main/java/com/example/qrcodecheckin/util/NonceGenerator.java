package com.example.qrcodecheckin.util;

import java.security.SecureRandom;
import java.util.Base64;

public class NonceGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generateNonce() {
        byte[] nonceBytes = new byte[16]; // 128-bit nonce
        random.nextBytes(nonceBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(nonceBytes);
    }
}

