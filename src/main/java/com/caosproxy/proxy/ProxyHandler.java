package com.caosproxy.proxy;

import com.caosproxy.rules.ChaosDecision;
import com.caosproxy.rules.RuleEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProxyHandler {

    private final WebClient webClient;
    private final RuleEngine ruleEngine;

    @Value("${target.base.url}")
    private String targetBaseUrl;

    public ProxyHandler(WebClient webClient,
                        RuleEngine ruleEngine) {
        this.webClient = webClient;
        this.ruleEngine = ruleEngine;
    }

    public Mono<ServerResponse> handle(ServerRequest request) {
        ChaosDecision decision = ruleEngine.evaluate(request.path(), request.method().name());

        if (decision.forceError()) {
            return ServerResponse
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("""
                    {
                      "error": "Internal Server Error",
                      "message": "Forced by ChaosProxy"
                    }
                """);
        }

        return forward(request);
    }

    private Mono<ServerResponse> forward(ServerRequest request) {
        String targetUrl = targetBaseUrl + request.uri().getRawPath();

        return webClient
                .method(request.method())
                .uri(targetUrl)
                .headers(headers -> headers.addAll(request.headers().asHttpHeaders()))
                .body(request.bodyToMono(byte[].class), byte[].class)
                .exchangeToMono(clientResponse ->
                        ServerResponse
                                .status(clientResponse.statusCode())
                                .headers(h -> h.addAll(clientResponse.headers().asHttpHeaders()))
                                .body(clientResponse.bodyToMono(byte[].class), byte[].class)
                );

    }
}
