package com.moh.property_reservation.reservation.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReservationsResponse(
        List<ReservationResponse> reservations,
        long totalRecords,
        BigDecimal totalMoneySpentOnFlats,
        BigDecimal totalMoneySpentOnHotels,
        BigDecimal totalMoneySpent,
        String cityWithMostReservations
) {
}
