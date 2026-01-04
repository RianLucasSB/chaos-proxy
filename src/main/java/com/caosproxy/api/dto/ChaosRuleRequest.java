package com.caosproxy.api.dto;

import com.caosproxy.rules.ActionMode;
import com.caosproxy.rules.ActionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChaosRuleRequest(
        @NotEmpty(message = "Path pattern must not be empty")
        String pathPattern,
        @NotEmpty(message = "Method must not be empty")
        @Pattern(
                regexp = "GET|POST|PUT|DELETE|PATCH|HEAD|OPTIONS|TRACE",
                message = "Method must be a valid HTTP method"
        )
        String method,
        @NotNull(message = "Probability must not be null")
        double probability,
        @NotNull(message = "Action must not be null")
        Action action

) {
        public record Action(
                @NotNull(message = "Action type must not be empty")
                ActionType type,
                @NotNull(message = "Action mode must not be empty")
                ActionMode mode,
                @NotNull(message = "Status code must not be null")
                int statusCode,
                String body
        ){}
}
