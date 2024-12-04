package com.moh.property_reservation.reservation.repository;

import com.moh.property_reservation.reservation.dto.ReservationFilter;
import com.moh.property_reservation.reservation.dto.ReservationResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ReservationRepositoryCustom {
    List<ReservationResponse> findReservationsBy(ReservationFilter filter, Pageable pageable);
    long countReservationsBy(ReservationFilter filter);
    BigDecimal sumTotalMoneySpentOnFlat();
    BigDecimal sumTotalMoneySpentOnHotel();
    String cityWithMostReservations();
}
