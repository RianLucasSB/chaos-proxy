package com.chaosproxy.api;


import com.chaosproxy.api.dto.ChaosRuleRequest;
import com.chaosproxy.api.dto.ChaosRuleResponse;
import com.chaosproxy.mapper.ChaosRuleMapper;
import com.chaosproxy.rules.RuleEngine;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rules")
public class RulesController {

    private final RuleEngine ruleEngine;

    public RulesController(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody ChaosRuleRequest rule) {
        ruleEngine.addRule(ChaosRuleMapper.toChaosRule(rule));
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @GetMapping
    public Flux<ChaosRuleResponse> getAll() {
        return Flux.fromStream(ruleEngine.getRules().stream().map(ChaosRuleMapper::toChaosRuleResponse));
    }
}
