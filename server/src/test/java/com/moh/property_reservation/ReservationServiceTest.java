package com.moh.property_reservation;

import com.moh.property_reservation.property.domain.PropertyType;
import com.moh.property_reservation.property.repository.PropertyRepository;
import com.moh.property_reservation.reservation.dto.ReservationFilter;
import com.moh.property_reservation.reservation.dto.ReservationResponse;
import com.moh.property_reservation.reservation.dto.ReservationsResponse;
import com.moh.property_reservation.reservation.repository.ReservationRepository;
import com.moh.property_reservation.reservation.service.ReservationService;
import com.moh.property_reservation.reservation.service.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void setUp() {
        reservationService = new ReservationServiceImpl(reservationRepository, propertyRepository);
    }

    @Test
    public void testGetReservations() {
        // given
        ReservationResponse reservation = new ReservationResponse(
                "TEST",
                PropertyType.HOTEL_ROOM.name(),
                "test city",
                "test country",
                "test address",
                new BigDecimal("200"),
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
        ReservationFilter reservationFilter = new ReservationFilter(
                null,
                null,
                null,
                null,
                null,
                null
        );
        given(reservationRepository.findReservationsBy(reservationFilter, PageRequest.of(0, 10)))
                .willReturn(List.of(reservation));
        given(reservationRepository.countReservationsBy(reservationFilter)).willReturn(1L);
        given(reservationRepository.sumTotalMoneySpentOnFlat()).willReturn(BigDecimal.ZERO);
        given(reservationRepository.sumTotalMoneySpentOnHotel()).willReturn(new BigDecimal("200"));
        given(reservationRepository.cityWithMostReservations()).willReturn("test city");
        // when
        ReservationsResponse response = reservationService.getReservations(reservationFilter, PageRequest.of(0, 10));
        // then
        then(response.reservations().contains(reservation)).isTrue();
        then(response.totalRecords()).isEqualTo(1);
        then(response.totalMoneySpentOnFlats()).isEqualTo(BigDecimal.ZERO);
        then(response.totalMoneySpentOnHotels()).isEqualTo(new BigDecimal("200"));
        then(response.totalMoneySpent()).isEqualTo(new BigDecimal("200"));
        then(response.cityWithMostReservations()).isEqualTo("test city");
    }

}
