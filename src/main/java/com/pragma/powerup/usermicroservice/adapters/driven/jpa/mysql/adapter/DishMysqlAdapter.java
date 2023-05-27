package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.ConnectionErrorException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonIsNotOwnerException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.ICategoryPersistencePort;
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

import java.util.Optional;

@RequiredArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    @Value("${microservice-user}")
    private String baseUrl;

    private static final String SERVICE = "/user/get-user-id-by-email/{email}";

    @Override
    public void createDish(Dish dish) {
        Long userId = getUserIdByEmail();
        Restaurant restaurant =  restaurantPersistencePort.getRestaurantById(dish.getRestaurantId());
        categoryPersistencePort.getCategoryById(dish.getCategoryId());
        if(userId!=null && userId.equals(restaurant.getOwnerId())){
            dish.setId(0L);
            dish.setActive(true);
            dishRepository.save(dishEntityMapper.toDishEntity(dish));
        }else{
            throw new PersonIsNotOwnerException();
        }
    }

    @Override
    public void updateDish(Dish dish) {
        Dish currentDish = getDish(dish);
        Long userId = getUserIdByEmail();
        Restaurant restaurant =  restaurantPersistencePort.getRestaurantById(currentDish.getRestaurantId());
        if(userId!=null && userId.equals(restaurant.getOwnerId())){
            setValues(currentDish, dish);
            dishRepository.save(dishEntityMapper.toDishEntity(currentDish));
        }else{
            throw new PersonIsNotOwnerException();
        }
    }

    private void setValues(Dish currentDish, Dish dish) {
        currentDish.setDescription(dish.getDescription());
        currentDish.setPrice(dish.getPrice());
    }

    private Dish getDish(Dish dish){
        Optional<DishEntity> optionalDish = dishRepository.findById(dish.getId());
        return optionalDish.map(dishEntityMapper::entityToDish).orElseThrow(NoDataFoundException::new);
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

