package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.ConnectionErrorException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonIsNotOwnerException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Value("${microservice-user}")
    private String baseUrl;
    private static final String SERVICE = "/user/get-rol-by-user-id/{ownerId}";

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        if(restaurantRepository.existsByNitIgnoreCase(restaurant.getNit())){
            throw new RestaurantAlreadyExistsException();
        }
        String role = getOwnerIdRole(restaurant.getOwnerId());
        if (Constants.OWNER_ROLE_NAME.equals(role)){
            restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
        }
        else{
            throw new PersonIsNotOwnerException();
        }
    }

    private String getOwnerIdRole(Long ownerId){
        WebClient webClient = WebClient.create(baseUrl);
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(SERVICE).build(ownerId))
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, RestaurantMysqlAdapter::apiError)
                .bodyToMono(String.class)
                .block();
    }

    private static Mono<ConnectionErrorException> apiError(ClientResponse response) {
        throw new ConnectionErrorException();
    }
}
