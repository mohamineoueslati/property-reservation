package com.moh.property_reservation.property.dto;

import java.util.List;

public record PropertiesResponse(
        List<PropertyResponse> properties,
        long totalRecords
) {
}
