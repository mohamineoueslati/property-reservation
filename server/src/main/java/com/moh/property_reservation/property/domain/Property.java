package com.moh.property_reservation.property.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity(name = "Property")
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "property_type")
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "city", nullable = false, length = 25)
    private String city;

    @Column(name = "country", nullable = false, length = 25)
    private String country;
}
