package com.moh.property_reservation.reservation.service;

import com.moh.property_reservation.property.domain.Property;
import com.moh.property_reservation.property.domain.PropertyType;
import com.moh.property_reservation.property.repository.PropertyRepository;
import com.moh.property_reservation.reservation.domain.Reservation;
import com.moh.property_reservation.reservation.dto.NewReservation;
import com.moh.property_reservation.reservation.dto.ReservationFilter;
import com.moh.property_reservation.reservation.dto.ReservationResponse;
import com.moh.property_reservation.reservation.dto.ReservationsResponse;
import com.moh.property_reservation.reservation.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final float HOTEL_DISCOUNT = 0.15f;
    private final float FLAT_DISCOUNT = 0.1f;
    private final float TAX = 0.05f;
    private final int TAX_DAYS = 10;

    private final ReservationRepository reservationRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public ReservationsResponse getReservations(ReservationFilter filter, Pageable pageable) {
        List<ReservationResponse> reservations = reservationRepository.findReservationsBy(filter, pageable);
        long totalRecords = reservationRepository.countReservationsBy(filter);
        BigDecimal moneySpentOnFlats = reservationRepository.sumTotalMoneySpentOnFlat();
        BigDecimal moneySpentOnHotels = reservationRepository.sumTotalMoneySpentOnHotel();
        String cityWithMostReservations = reservationRepository.cityWithMostReservations();
        return new ReservationsResponse(
                reservations,
                totalRecords,
                moneySpentOnFlats,
                moneySpentOnHotels,
                moneySpentOnFlats == null || moneySpentOnHotels == null ? null : moneySpentOnFlats.add(moneySpentOnHotels),
                cityWithMostReservations
        );
    }

    @Override
    @Transactional
    public ReservationResponse makeReservation(NewReservation reservation) {
        // Save property
        Property property = new Property();
        property.setName(reservation.propertyName());
        property.setAddress(reservation.address());
        property.setCity(reservation.city());
        property.setCountry(reservation.country());
        property.setPropertyType(reservation.propertyType());
        property.setPrice(reservation.price());
        propertyRepository.save(property);

        // Calculate Money spent
        BigDecimal moneySpent = new BigDecimal(0);
        long numberOfDays = Duration.between(reservation.fromDate(), reservation.toDate()).toDays();
        BigDecimal price = reservation.price().multiply(new BigDecimal(numberOfDays));
        if (reservation.propertyType() == PropertyType.HOTEL_ROOM) {
            moneySpent = price.multiply(new BigDecimal(1 - HOTEL_DISCOUNT));
        } else if (reservation.propertyType() == PropertyType.FLAT) {
            moneySpent = price.multiply(new BigDecimal(1 - FLAT_DISCOUNT));
        }
        if (numberOfDays > TAX_DAYS) {
            moneySpent = moneySpent.add(moneySpent.multiply(new BigDecimal(TAX)));
        }

        // Save reservation
        Reservation dbReservation = new Reservation();
        dbReservation.setFromDate(reservation.fromDate());
        dbReservation.setToDate(reservation.toDate());
        dbReservation.setProperty(property);
        dbReservation.setMoneySpent(moneySpent);
        reservationRepository.save(dbReservation);

        return new ReservationResponse(
                reservation.propertyName(),
                reservation.propertyType().name(),
                reservation.city(),
                reservation.country(),
                reservation.address(),
                moneySpent,
                reservation.fromDate(),
                reservation.toDate()
        );
     }
}
