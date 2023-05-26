package com.pragma.powerup.usermicroservice.configuration;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String REGEX_PHONE = "^([0-9]{10,11})|(\\+[0-9]{12,13})$";
    public static final String REGEX_NIT_DIGITS = "^\\d{5,12}$";
    public static final String REGEX_BY_NAME = "^([\\d]*[\\s]*[a-zA-Z][\\w\\s]*)";

    public static final String OWNER_ROLE_NAME = "ROLE_OWNER";
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String RESTAURANT_DELETED_MESSAGE = "Restaurant deleted successfully";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String SWAGGER_TITLE_MESSAGE = "Restaurant API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "Restaurant microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";

    public static final String RESTAURANT_ALREADY_EXISTS_MESSAGE = "A restaurant already exists with the NIT number provided";
    public static final String CONNECTION_PROBLEM = "Connection issues";
    public static final String PERSON_IS_NOT_OWNER = "Person isn't an owner";
    public static final String RESTAURANT_DOES_NOT_EXIST = "The restaurant doesn't exist";
    public static final String RESTAURANT_NAME_MANDATORY = "The restaurant name is mandatory";
    public static final String RESTAURANT_NIT_MANDATORY = "The restaurant NIT is mandatory";
    public static final String RESTAURANT_ADDRESS_MANDATORY = "The restaurant address is mandatory";
    public static final String RESTAURANT_URL_LOGO_MANDATORY = "The restaurant url logo is mandatory";
    public static final String RESTAURANT_OWNER_MANDATORY = "The restaurant owner is mandatory";
    public static final String RESTAURANT_PHONE_MANDATORY = "The restaurant phone is mandatory";
    public static final String PHONE_IS_INVALID = "Phone is invalid";
    public static final String NIT_IS_INVALID = "NIT is invalid";
    public static final String NAME_IS_INVALID = "Name is invalid";

    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
}
