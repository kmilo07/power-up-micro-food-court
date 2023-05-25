package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishRequestDto {
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private Double price;
    private Long restaurantId;
    private String urlImage;
    private Boolean active;
}
