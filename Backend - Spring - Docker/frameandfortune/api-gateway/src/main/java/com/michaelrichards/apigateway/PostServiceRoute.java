package com.michaelrichards.apigateway;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

@Configuration
public class PostServiceRoute {

    @Bean
    public RouterFunction<ServerResponse> postsRoute(){
        return GatewayRouterFunctions.route("post-service")
                .route(RequestPredicates.path("api/v1/posts/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:8083"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "postsServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> postsFallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .route(RequestPredicates.path("/fallbackRoute"), request ->
                        ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Follow service is down"))
                .build();
    }
}
