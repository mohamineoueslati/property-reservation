package com.moh.property_reservation.property.repository;

import com.moh.property_reservation.property.dto.PropertyFilter;
import com.moh.property_reservation.property.dto.PropertyResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyRepositoryCustom {
    List<PropertyResponse> findPropertiesBy(PropertyFilter filter, Pageable pageable);
    long countPropertiesBy(PropertyFilter filter);
}
