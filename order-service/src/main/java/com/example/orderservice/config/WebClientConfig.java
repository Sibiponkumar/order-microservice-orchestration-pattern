package com.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Profile("!docker")
    @Bean(name = "inventoryWebClient")
    public WebClient inventoryLocal() {
        return WebClient.create("http://localhost:8081");
    }

    @Profile("docker")
    @Bean(name = "inventoryWebClient")
    public WebClient inventoryDocker() {
        return WebClient.create("http://inventory-service:8081");
    }

    @Profile("!docker")
    @Bean(name = "paymentWebClient")
    public WebClient paymentLocal() {
        return WebClient.create("http://localhost:8082");
    }

    @Profile("docker")
    @Bean(name = "paymentWebClient")
    public WebClient paymentDocker() {
        return WebClient.create("http://payment-service:8082");
    }

    @Profile("!docker")
    @Bean(name = "shipmentWebClient")
    public WebClient shipmentLocal() {
        return WebClient.create("http://localhost:8083");
    }

    @Profile("docker")
    @Bean(name = "shipmentWebClient")
    public WebClient shipmentDocker() {
        return WebClient.create("http://shipment-service:8083");
    }
}
