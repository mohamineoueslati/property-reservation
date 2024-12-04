package com.moh.property_reservation.property.dto;

import com.moh.property_reservation.property.domain.PropertyType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PropertyFilter(
        String searchQuery,
        PropertyType propertyType,
        @Min(0) BigDecimal minPrice,
        @Min(0) BigDecimal maxPrice,
        @NotNull OffsetDateTime fromDate,
        @NotNull OffsetDateTime toDate,
        Boolean available
) {
}
