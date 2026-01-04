package com.caosproxy.rules;

import com.caosproxy.utils.PathMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class RuleMatcher {

    public boolean matches(ChaosRule rule, ServerRequest request) {
        return matchesMethod(rule.method(), request.method())
                && matchesPath(rule.pathPattern(), request.path());
    }

    private boolean matchesMethod(String ruleMethod, HttpMethod requestMethod) {
        return ruleMethod == null || ruleMethod.equals(requestMethod.name());
    }

    private boolean matchesPath(String pattern, String path) {
        return PathMatcher.match(pattern, path);
    }
}
