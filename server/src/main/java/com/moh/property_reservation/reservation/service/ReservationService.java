package com.moh.property_reservation.reservation.service;

import com.moh.property_reservation.reservation.dto.NewReservation;
import com.moh.property_reservation.reservation.dto.ReservationFilter;
import com.moh.property_reservation.reservation.dto.ReservationResponse;
import com.moh.property_reservation.reservation.dto.ReservationsResponse;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
    ReservationsResponse getReservations(ReservationFilter filter, Pageable pageable);
    ReservationResponse makeReservation(NewReservation reservation);
}
