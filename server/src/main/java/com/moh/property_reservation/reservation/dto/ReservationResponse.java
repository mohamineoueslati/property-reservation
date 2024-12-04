package com.moh.property_reservation.reservation.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ReservationResponse(
        String propertyName,
        String propertyType,
        String city,
        String country,
        String address,
        BigDecimal moneySpent,
        OffsetDateTime fromDate,
        OffsetDateTime toDate
) {
}
