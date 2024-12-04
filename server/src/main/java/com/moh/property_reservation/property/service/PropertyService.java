package com.moh.property_reservation.property.service;

import org.springframework.data.domain.Pageable;

import com.moh.property_reservation.property.dto.PropertyFilter;
import com.moh.property_reservation.property.dto.PropertiesResponse;

public interface PropertyService {
    PropertiesResponse getProperties(PropertyFilter filter, Pageable pageable);
}
