package com.fsoft.gateway.filter;

import com.fsoft.gateway.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyGatewayFilterFactory extends AbstractGatewayFilterFactory<ApiKeyGatewayFilterFactory.Config> {

    @Autowired
    private AuthenticationService authenticationService;

    public final Logger logger = LoggerFactory.getLogger(ApiKeyGatewayFilterFactory.class);

    public ApiKeyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logger.info("Pre GatewayFilter logging: " + config.getBaseMessage());

            String apiKey = exchange.getRequest().getHeaders().getFirst("API-KEY");

            if (!authenticationService.isValidApiKey(apiKey)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                logger.error("API key is invalid: " + apiKey);
                return exchange.getResponse().setComplete();
            }

            logger.info("Post GatewayFilter logging: " + config.getBaseMessage());
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

        public Config() { }

        public Config(String baseMessage, boolean preLogger, boolean postLogger) {
            this.baseMessage = baseMessage;
            this.preLogger = preLogger;
            this.postLogger = postLogger;
        }

        public String getBaseMessage() {
            return baseMessage;
        }

        public void setBaseMessage(String baseMessage) {
            this.baseMessage = baseMessage;
        }

        public boolean isPreLogger() {
            return preLogger;
        }

        public void setPreLogger(boolean preLogger) {
            this.preLogger = preLogger;
        }

        public boolean isPostLogger() {
            return postLogger;
        }

        public void setPostLogger(boolean postLogger) {
            this.postLogger = postLogger;
        }
    }
}
