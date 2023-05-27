package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishRequestDto {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 150)
    private String name;
    @NotNull
    private Long categoryId;
    @NotBlank
    @Size(max = 250)
    private String description;
    @NotNull
    @Positive
    private Integer price;
    @NotNull
    private Long restaurantId;
    @NotBlank
    private String urlImage;
    private Boolean active;
}
