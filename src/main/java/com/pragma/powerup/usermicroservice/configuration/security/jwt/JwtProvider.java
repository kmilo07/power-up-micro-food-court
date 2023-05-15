package com.pragma.powerup.usermicroservice.configuration.security.jwt;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class JwtProvider {

    private static final String URL_USER_MICROSERVICE = "http://localhost:8090";
    private static final String SERVICE = "/auth/token-is-valid/{token}";

    private final WebClient webClient;

    public JwtProvider() {
        this.webClient = WebClient.create(URL_USER_MICROSERVICE);
    }

    public Boolean tokenIsValid(String token){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(SERVICE).build(token))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

    }

}
