package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Column(name = "OWNER_ID")
    private Long ownerId;
    private String phone;
    @Column(name = "URL_LOGO")
    private String urlLogo;
    private String nit;
}
