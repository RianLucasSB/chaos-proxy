package com.chaosproxy.rules;

public record ChaosDecision(
        boolean forceError,
        int statusCode,
        String responseBody
) {

    public static ChaosDecision passThrough() {
        return new ChaosDecision(false, 0, null);
    }

    public static ChaosDecision forceError(int statusCode, String body) {
        return new ChaosDecision(true, statusCode, body);
    }
}
