package com.caosproxy.config;

import com.caosproxy.proxy.ProxyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProxyRouter {

    @Bean
    RouterFunction<ServerResponse> proxyRoutes(ProxyHandler handler) {
        return RouterFunctions.route(
                RequestPredicates.all()
                        .and(RequestPredicates.path("/rules").negate())
                        .and(RequestPredicates.path("/rules/**").negate()),
                handler::handle
        );
    }
}