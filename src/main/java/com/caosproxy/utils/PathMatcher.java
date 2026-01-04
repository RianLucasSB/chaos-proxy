package com.caosproxy.utils;

import org.springframework.util.AntPathMatcher;

public class PathMatcher {

    private static final AntPathMatcher matcher = new AntPathMatcher();

    public static boolean match(String pattern, String path) {
        return matcher.match(pattern, path);
    }
}
