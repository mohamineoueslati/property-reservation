package com.moh.property_reservation.property.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moh.property_reservation.property.dto.PropertyFilter;
import com.moh.property_reservation.property.dto.PropertiesResponse;
import com.moh.property_reservation.property.service.PropertyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public PropertiesResponse getProperties(@Valid PropertyFilter filter, Pageable pageable) {
        return propertyService.getProperties(filter, pageable);
    }

}
