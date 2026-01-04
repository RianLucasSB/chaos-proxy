package com.caosproxy.rules;

import org.springframework.http.HttpMethod;

public record ChaosRule(
        String id,
        String pathPattern,
        HttpMethod method,
        double probability,
        ChaosAction action
) {}