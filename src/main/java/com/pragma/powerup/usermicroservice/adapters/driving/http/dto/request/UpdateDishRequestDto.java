package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateDishRequestDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    @Size(max = 250)
    private String description;
    @NotNull
    @Positive
    private Integer price;
}
