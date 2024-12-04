package com.moh.property_reservation.reservation.repository;

import com.moh.property_reservation.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
}
