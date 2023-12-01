//package com.example.SimplestCRUDExample.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class WebClientConfig {
//
//    @Value("${api.url}")
//    private String apiUrl;
//
//    @Value("${api.key}")
//    private String apiKey;
//
//    @Bean
//    public WebClient.Builder webClientBuilder() {
//        return WebClient.builder()
//                .baseUrl(apiUrl)
//                .defaultHeader("apikey", apiKey);
//    }
//}
