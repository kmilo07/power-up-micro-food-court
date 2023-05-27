package com.pragma.powerup.usermicroservice.domain.model;

public class Dish {
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private Integer price;
    private Long restaurantId;
    private String urlImage;
    private Boolean active;

    public Dish(Long id, String name, Long categoryId, String description, Integer price, Long restaurantId, String urlImage, Boolean active) {
        setId(id);
        setName(name);
        setCategoryId(categoryId);
        setDescription(description);
        setPrice(price);
        setRestaurantId(restaurantId);
        setUrlImage(urlImage);
        setActive(active);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
