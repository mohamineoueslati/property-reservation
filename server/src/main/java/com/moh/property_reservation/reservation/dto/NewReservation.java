package com.moh.property_reservation.reservation.dto;

import com.moh.property_reservation.property.domain.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record NewReservation(
        @NotBlank String propertyName,
        @NotNull PropertyType propertyType,
        @NotBlank String city,
        @NotBlank String country,
        @NotBlank String address,
        @Positive BigDecimal price,
        @NotNull OffsetDateTime fromDate,
        @NotNull OffsetDateTime toDate
) {
}
