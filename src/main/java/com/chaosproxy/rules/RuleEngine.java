package com.chaosproxy.rules;

import com.chaosproxy.utils.PathMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class RuleEngine {
    private CopyOnWriteArrayList<ChaosRule> rules = new CopyOnWriteArrayList<>();

    public void addRule(ChaosRule rule) {
        rules.add(rule);
    }

    public ChaosDecision evaluate(String path, String method) {
        for (ChaosRule rule : rules) {
            if (rule.method().equalsIgnoreCase(method) && PathMatcher.match(rule.pathPattern(), path)) {
                return toDecision(rule.action());
            }
        }
        return ChaosDecision.passThrough();
    }

    public List<ChaosRule> getRules() {
        return rules;
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
