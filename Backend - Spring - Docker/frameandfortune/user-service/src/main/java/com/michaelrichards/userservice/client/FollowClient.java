package com.michaelrichards.userservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Service
public class FollowClient {

    private final RestClient restClient;

    private final String baseUrl;


    public FollowClient(@LoadBalanced RestClient.Builder loadBalancedRestClientBuilder, @Value("${services.follow.baseurl}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restClient = loadBalancedRestClientBuilder.baseUrl(baseUrl)
                .defaultStatusHandler(HttpStatusCode::isError, ((request, response) -> {
                    throw new HttpStatusCodeException(response.getStatusCode(), "Error Calling " + request.getURI()) {
                    };
                }))
                .build();

    }


    public void acceptAllFollowRequest(Long userId) {
    }
}
