package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantRequestDtoTest {

    private final String GOOD_NAME = "Las delicias";
    private final String GOOD_ADDRESS = "calle 40 # 44 00";
    private final Long GOOD_OWNER = 2L;
    private final String GOOD_PHONE = "3123322221";
    private final String GOOD_URL = "urlDePrueba";
    private final String GOOD_NIT = "123456789";

    @Autowired
    private Validator validator;
    private RestaurantRequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,GOOD_OWNER,GOOD_PHONE,GOOD_URL,GOOD_NIT);
    }

    @DisplayName("Valida si el campo nombre es vacío")
    @Test
    void validNameRequestDtoEmpty(){
        String EMPTY_NAME = "";
        requestDto =  new RestaurantRequestDto(EMPTY_NAME,GOOD_ADDRESS,GOOD_OWNER,GOOD_PHONE,GOOD_URL,GOOD_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(2, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }

    }

    @DisplayName("Valida si el campo nombre esta conformado solo por numeros")
    @Test
    void validNameRequestDtoBadOnlyNumber(){
        String BAD_NAME = "421";
        requestDto =  new RestaurantRequestDto(BAD_NAME,GOOD_ADDRESS,GOOD_OWNER,GOOD_PHONE,GOOD_URL,GOOD_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @DisplayName("Valida si el campo dirección es vacío")
    @Test
    void validAddressRequestDtoEmpty(){
        String EMPTY_ADDRESS = "";
        requestDto =  new RestaurantRequestDto(GOOD_NAME,EMPTY_ADDRESS,GOOD_OWNER,GOOD_PHONE,GOOD_URL,GOOD_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @DisplayName("Valida si el campo propietario esta vacío")
    @Test
    void validOwnerRequestDtoEmpty(){
        Long EMPTY_OWNER = null;
        requestDto =  new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,EMPTY_OWNER,GOOD_PHONE,GOOD_URL,GOOD_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @DisplayName("Valida si el campo telefono es vacío")
    @Test
    void validPhoneRequestDtoEmpty(){
        String EMPTY_PHONE = "";
        requestDto =  new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,GOOD_OWNER,EMPTY_PHONE,GOOD_URL,GOOD_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(2, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }

    }

    @DisplayName("Valida si el campo telefono es cuenta con la longitud menor a la minima")
    @Test
    void validPhoneRequestDtoBadPhoneMin(){
        String BAD_MIN_PHONE = "23";
        requestDto =  new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,GOOD_OWNER,BAD_MIN_PHONE,GOOD_URL,GOOD_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @DisplayName("Valida si el campo telefono es cuenta con la longitur superiro a max")
    @Test
    void validRequestDtoBadPhoneMax(){
        String BAD_PHONE_MAX = "300000000000000000000";
        requestDto =  new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,GOOD_OWNER,BAD_PHONE_MAX,GOOD_URL,GOOD_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @DisplayName("Valida si el campo url es vacio")
    @Test
    void validUrlRequestDtoBadEmpty(){
        String EMPTY_URL = "";
        requestDto =  new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,GOOD_OWNER,GOOD_PHONE,EMPTY_URL,GOOD_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @DisplayName("Valida si el campo nit es vacío")
    @Test
    void validNitRequestDtoEmpty(){
        String EMPTY_NIT = "";
        requestDto =  new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,GOOD_OWNER,GOOD_PHONE,GOOD_URL,EMPTY_NIT);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }

    }

    @DisplayName("Valida si el campo NIT es cuenta con longitud menor a la minima")
    @Test
    void validNitRequestDtoBadNitMin(){
        String BAD_MIN_PHONE = "23";
        requestDto =  new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,GOOD_OWNER,GOOD_PHONE,GOOD_URL,BAD_MIN_PHONE);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @DisplayName("Valida si el campo NIT cuenta con mayor numero de los permitidos")
    @Test
    void validNITRequestDtoBadNITMax(){
        String BAD_NIT_MAX = "300000000000000000000";
        requestDto =  new RestaurantRequestDto(GOOD_NAME,GOOD_ADDRESS,GOOD_OWNER,GOOD_PHONE,GOOD_URL,BAD_NIT_MAX);
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(requestDto);

        assertEquals(1, violations.size());

        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @Test
    void getName() {
        assertEquals(GOOD_NAME, requestDto.getName());
    }

    @Test
    void getAddress() {
        assertEquals(GOOD_ADDRESS, requestDto.getAddress());
    }

    @Test
    void getOwnerId() {
        assertEquals(GOOD_OWNER, requestDto.getOwnerId());
    }

    @Test
    void getPhone() {
        assertEquals(GOOD_PHONE, requestDto.getPhone());
    }

    @Test
    void getUrlLogo() {
        assertEquals(GOOD_URL, requestDto.getUrlLogo());
    }

    @Test
    void getNit() {
        assertEquals(GOOD_NIT, requestDto.getNit());
    }
}