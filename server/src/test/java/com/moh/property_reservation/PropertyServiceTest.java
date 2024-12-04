package com.moh.property_reservation;

import com.moh.property_reservation.property.domain.PropertyType;
import com.moh.property_reservation.property.dto.PropertiesResponse;
import com.moh.property_reservation.property.dto.PropertyFilter;
import com.moh.property_reservation.property.dto.PropertyResponse;
import com.moh.property_reservation.property.repository.PropertyRepository;
import com.moh.property_reservation.property.service.PropertyService;
import com.moh.property_reservation.property.service.PropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    private PropertyService propertyService;
    @Mock
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void setUp() {
        propertyService = new PropertyServiceImpl(propertyRepository);
    }

    @Test
    public void testGetProperties() {
        // given
        PropertyResponse property = new PropertyResponse(
                "TEST",
                PropertyType.FLAT.name(),
                "test city",
                "test country",
                "test address",
                new BigDecimal("100.00"),
                true
        );
        PropertyFilter filter = new PropertyFilter(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        given(propertyRepository.findPropertiesBy(filter, PageRequest.of(0, 10)))
                .willReturn(List.of(property));
        given(propertyRepository.countPropertiesBy(filter)).willReturn(1L);
        // when
        PropertiesResponse response = propertyService.getProperties(filter, PageRequest.of(0, 10));
        // then
        then(response.properties().contains(property)).isTrue();
        then(response.properties().size() == 1).isTrue();
        then(response.totalRecords() == 1).isTrue();
    }
}
