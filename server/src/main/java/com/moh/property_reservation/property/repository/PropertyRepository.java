package com.moh.property_reservation.property.repository;

import com.moh.property_reservation.property.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long>, PropertyRepositoryCustom {
}
