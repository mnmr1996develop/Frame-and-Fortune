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
public class UserServiceRoute {

    @Bean
    public RouterFunction<ServerResponse> userRoute(){
        return GatewayRouterFunctions.route("user-service")
                .route(RequestPredicates.path("api/v1/users/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "userServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userFallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .route(RequestPredicates.path("/fallbackRoute"), request ->
                        ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("User service is down"))
                .build();
    }

}
