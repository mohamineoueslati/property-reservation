package com.moh.property_reservation.reservation.dto;

import com.moh.property_reservation.property.domain.PropertyType;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ReservationFilter(
        String searchQuery,
        PropertyType propertyType,
        @Min(0) BigDecimal minPrice,
        @Min(0) BigDecimal maxPrice,
        OffsetDateTime fromDate,
        OffsetDateTime toDate
) {
}
