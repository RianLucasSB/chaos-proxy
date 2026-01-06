package com.chaosproxy.rules;

import lombok.Builder;

@Builder
public record ChaosRule(
        String id,
        String pathPattern,
        String method,
        double probability,
        ChaosAction action
) {}