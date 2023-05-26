package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.ConnectionErrorException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonIsNotOwnerException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
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
public class DishMysqlAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    @Value("${microservice-user}")
    private String baseUrl;

    private static final String SERVICE = "/user/get-user-id-by-email/{email}";

    @Override
    public void createDish(Dish dish) {
        Long userId = getUserIdByEmail();
        Restaurant restaurant =  restaurantPersistencePort.getRestaurantById(dish.getRestaurantId());
        if(userId!=null && userId.equals(restaurant.getOwnerId())){
            dishRepository.save(dishEntityMapper.toDishEntity(dish));
        }else{
            throw new PersonIsNotOwnerException();
        }
    }


    private Long getUserIdByEmail(){
        WebClient webClient = WebClient.create(baseUrl);
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(SERVICE).build(email))
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, DishMysqlAdapter::apiError)
                .bodyToMono(Long.class)
                .block();
    }

    private static Mono<ConnectionErrorException> apiError(ClientResponse response) {
        throw new ConnectionErrorException();
    }
}

/*
* "1. Solo el propietario de un restaurante puede crear platos.
2. Para crear un plato se deben solicitar los siguientes campos obligatorios:
Nombre del plato, precio del plato (en números enteros positivos y mayores a 0), Descripción, UrlImagen y la categoria.
3. To do plato debe estar asociado a un restaurante.
4. por defecto cada plato recien creado tiene la variable activa en true. "
* */
