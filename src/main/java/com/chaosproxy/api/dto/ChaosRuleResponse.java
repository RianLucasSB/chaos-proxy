package com.chaosproxy.api.dto;


import lombok.Builder;

@Builder
public record ChaosRuleResponse (
    String pathPattern,
    String method,
    double probability,
    Action action

) {
        @Builder
        public record Action(
                String type,
                String mode,
                int statusCode,
                String body
        ){}
}
