package com.caosproxy.mapper;

import com.caosproxy.api.dto.ChaosRuleRequest;
import com.caosproxy.api.dto.ChaosRuleResponse;
import com.caosproxy.rules.ChaosAction;
import com.caosproxy.rules.ChaosRule;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChaosRuleMapper {
    public static ChaosRule toChaosRule(ChaosRuleRequest request) {
        return ChaosRule.builder()
                .id(request.method() + ":" + request.pathPattern().replace("/", "_"))
                .method(request.method())
                .pathPattern(request.pathPattern())
                .probability(request.probability())
                .action(toChaosAction(request))
                .build();
    }

    public static ChaosRuleResponse toChaosRuleResponse(ChaosRule chaosRule) {
        return ChaosRuleResponse.builder()
                .method(chaosRule.method())
                .pathPattern(chaosRule.pathPattern())
                .probability(chaosRule.probability())
                .action(ChaosRuleResponse.Action.builder()
                        .mode(chaosRule.action().mode().toString())
                        .type(chaosRule.action().type().toString())
                        .statusCode(chaosRule.action().statusCode())
                        .body(chaosRule.action().body())
                        .build()
                )
                .build();
    }

    private static ChaosAction toChaosAction(ChaosRuleRequest request) {
        return new ChaosAction(
                request.action().type(),
                request.action().mode(),
                request.action().statusCode(),
                request.action().body()
        );
    }
}
