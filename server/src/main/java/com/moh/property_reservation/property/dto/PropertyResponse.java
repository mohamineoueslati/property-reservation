package com.moh.property_reservation.property.dto;

import java.math.BigDecimal;

public record PropertyResponse(
        String name,
        String propertyType,
        String city,
        String country,
        String address,
        BigDecimal price,
        Boolean available
) {
}
