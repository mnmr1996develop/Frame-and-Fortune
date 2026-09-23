package com.michaelrichards.followservice.client;

import com.michaelrichards.followservice.dto.ExistsResponse;
import com.michaelrichards.followservice.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Service
public class UserClient {


    private final RestClient restClient;

    private final String baseUrl;


    public UserClient(@LoadBalanced RestClient.Builder loadBalancedRestClientBuilder, @Value("${services.user.baseurl}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restClient = loadBalancedRestClientBuilder.baseUrl(baseUrl)
                .defaultStatusHandler(HttpStatusCode::isError, ((request, response) -> {
                    throw new HttpStatusCodeException(response.getStatusCode(), "Error Calling " + request.getURI()) {
                    };
                }))
                .build();

    }

    public boolean userExists(Long userId) {

        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/{userId}/exists")
                .buildAndExpand(userId).toUriString();

        log.info("user exists: {}", url);

        try {
            ExistsResponse response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            assert response != null;
            return response.exists();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

   public void updateLastSeen(Long userId) {
       String url = UriComponentsBuilder.fromUriString(baseUrl)
               .path("/{userId}/lastSeen")
               .buildAndExpand(userId).toUriString();

       restClient.patch()
               .uri(url)
               .retrieve()
               .body(new ParameterizedTypeReference<>() {
               });
   }


    public UserResponse getUserById(Long id) {

        log.info(baseUrl);

        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/{id}")
                .buildAndExpand(id)
                .toUriString();

        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
