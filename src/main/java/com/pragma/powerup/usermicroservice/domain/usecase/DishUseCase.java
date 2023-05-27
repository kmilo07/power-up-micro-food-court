package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.ConnectionErrorException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonIsNotOwnerException;
import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class DishUseCase implements IDishServicePort {
    @Value("${microservice-user}")
    private String baseUrl;

    private static final String SERVICE = "/user/get-user-id-by-email/{email}";
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createDish(Dish dish) {
        validateRestaurantOwner(dish);
        categoryPersistencePort.getCategoryById(dish.getCategoryId());
        dish.setId(0L);
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Dish dish) {
        Dish currentDish = getDishById(dish.getId());
        validateRestaurantOwner(currentDish);
        setValues(currentDish,dish);
        dishPersistencePort.saveDish(currentDish);
    }

    private Dish getDishById(Long dishId){
        return dishPersistencePort.getDishById(dishId);
    }

    private void setValues(Dish currentDish, Dish dish) {
        currentDish.setDescription(dish.getDescription());
        currentDish.setPrice(dish.getPrice());
    }

    private void validateRestaurantOwner(Dish dish){
        Long userId = getUserIdByEmail();
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(dish.getRestaurantId());
        if(userId == null || !userId.equals(restaurant.getOwnerId())){
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
                .onStatus(HttpStatusCode::isError, DishUseCase::apiError)
                .bodyToMono(Long.class)
                .block();
    }

    private static Mono<ConnectionErrorException> apiError(ClientResponse response) {
        throw new ConnectionErrorException();
    }
}
