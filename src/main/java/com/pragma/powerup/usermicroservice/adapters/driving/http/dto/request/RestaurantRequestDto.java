package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantRequestDto {
    @NotBlank(message = Constants.RESTAURANT_NAME_MANDATORY)
    @Pattern(regexp = Constants.REGEX_BY_NAME, message = Constants.NAME_IS_INVALID)
    private String name;
    @NotBlank(message = Constants.RESTAURANT_ADDRESS_MANDATORY)
    private String address;
    @NotNull(message = Constants.RESTAURANT_OWNER_MANDATORY)
    private Long ownerId;
    @NotBlank(message = Constants.RESTAURANT_PHONE_MANDATORY)
    @Pattern(regexp = Constants.REGEX_PHONE, message = Constants.PHONE_IS_INVALID)
    private String phone;
    @NotBlank(message = Constants.RESTAURANT_URL_LOGO_MANDATORY)
    private String urlLogo;
    @NotNull(message = Constants.RESTAURANT_NIT_MANDATORY)
    @Pattern(regexp = Constants.REGEX_NIT_DIGITS, message = Constants.NIT_IS_INVALID)
    private String nit;
}
