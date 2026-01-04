package com.caosproxy.rules;

public record ChaosAction(
    ActionType type,
    ActionMode mode,
    int statusCode,
    String body
) {}
