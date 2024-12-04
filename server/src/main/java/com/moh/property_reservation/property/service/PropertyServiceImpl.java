package com.moh.property_reservation.property.service;

import com.moh.property_reservation.property.dto.PropertyResponse;
import com.moh.property_reservation.property.repository.PropertyRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.moh.property_reservation.property.dto.PropertyFilter;
import com.moh.property_reservation.property.dto.PropertiesResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Override
    public PropertiesResponse getProperties(PropertyFilter filter, Pageable pageable) {
        List<PropertyResponse> properties = propertyRepository.findPropertiesBy(filter, pageable);
        long totalRecords = propertyRepository.countPropertiesBy(filter);
        return new PropertiesResponse(properties, totalRecords);
    }

}
