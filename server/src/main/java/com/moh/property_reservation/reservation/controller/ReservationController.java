package com.moh.property_reservation.reservation.controller;

import com.moh.property_reservation.reservation.dto.NewReservation;
import com.moh.property_reservation.reservation.dto.ReservationFilter;
import com.moh.property_reservation.reservation.dto.ReservationResponse;
import com.moh.property_reservation.reservation.dto.ReservationsResponse;
import com.moh.property_reservation.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ReservationsResponse getReservations(ReservationFilter filter, Pageable pageable) {
        return reservationService.getReservations(filter, pageable);
    }

    @PostMapping
    public ReservationResponse makeReservation(@RequestBody @Valid NewReservation newReservation) {
        return reservationService.makeReservation(newReservation);
    }

}
