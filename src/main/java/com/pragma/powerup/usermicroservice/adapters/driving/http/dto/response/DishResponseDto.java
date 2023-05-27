package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishResponseDto {
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private Integer price;
    private Long restaurantId;
    private String urlImage;
    private Boolean active;
}
