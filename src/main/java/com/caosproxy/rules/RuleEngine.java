package com.caosproxy.rules;

import com.caosproxy.utils.PathMatcher;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RuleEngine {
    private CopyOnWriteArrayList<ChaosRule> rules = new CopyOnWriteArrayList<>();

    public void addRule(ChaosRule rule) {
        rules.add(rule);
    }

    public ChaosDecision evaluate(String path, String method) {
        for (ChaosRule rule : rules) {
            if (rule.method().name().equalsIgnoreCase(method) && PathMatcher.match(rule.pathPattern(), path)) {
                return toDecision(rule.action());
            }
        }
        return ChaosDecision.passThrough();
    }

    private ChaosDecision toDecision(ChaosAction action) {

        if (action.type() == ActionType.ERROR
                && action.mode() == ActionMode.FORCED) {

            return ChaosDecision.forceError(
                    action.statusCode(),
                    action.body()
            );
        }

        return ChaosDecision.passThrough();
    }
}
