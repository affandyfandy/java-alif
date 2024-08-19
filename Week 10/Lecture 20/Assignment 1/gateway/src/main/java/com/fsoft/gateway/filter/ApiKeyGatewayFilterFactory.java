package com.fsoft.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyGatewayFilterFactory extends AbstractGatewayFilterFactory<ApiKeyGatewayFilterFactory.Config> {
    @Autowired
    private WebClient.Builder webClientBuilder;

    private static final Logger log = LoggerFactory.getLogger(ApiKeyGatewayFilterFactory.class);

    public ApiKeyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String apiKey = headers.getFirst("API-KEY");

            log.info("API key: " + apiKey);

            if (apiKey == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            log.info("After API key check");

            return webClientBuilder.build()
                    .get()
                    .uri("lb://localhost:8083/auth")
                    .header("API-KEY", apiKey)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError(),
                            response -> Mono.error(
                                    new RuntimeException("Invalid API key"
                                    )
                            ))
                    .bodyToMono(String.class)
                    .flatMap(response -> chain.filter(exchange));
        };
    }

    public static class Config {}
}
