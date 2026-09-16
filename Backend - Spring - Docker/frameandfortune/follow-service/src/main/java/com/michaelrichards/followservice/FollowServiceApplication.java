package com.michaelrichards.followservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FollowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FollowServiceApplication.class, args);
    }

}
