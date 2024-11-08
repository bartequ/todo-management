package org.bszabat.todomanagement.application.exception;

import java.security.SecureRandom;

public record ClientError(String message, String code) {
    private static final SecureRandom RANDOM = new SecureRandom();

    public ClientError(String message) {
        this(message, String.valueOf(RANDOM.nextLong()));
    }
}
