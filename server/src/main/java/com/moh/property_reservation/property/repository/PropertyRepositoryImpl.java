package com.moh.property_reservation.property.repository;

import com.moh.property_reservation.property.domain.Property;
import com.moh.property_reservation.property.domain.PropertyType;
import com.moh.property_reservation.property.dto.PropertyFilter;
import com.moh.property_reservation.property.dto.PropertyResponse;
import com.moh.property_reservation.reservation.domain.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyRepositoryImpl implements PropertyRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("all")
    public List<PropertyResponse> findPropertiesBy(PropertyFilter filter, Pageable pageable)  {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<Property> property = query.from(Property.class);

        // Check availability
        Expression<Object> availability = checkAvailability(cb, query, property, filter);

        // Select fields
        query.multiselect(
                property.get("name").alias("name"),
                property.get("propertyType").alias("propertyType"),
                property.get("address").alias("address"),
                property.get("city").alias("city"),
                property.get("country").alias("country"),
                property.get("price").alias("price"),
                availability.alias("availability")
        );

        // Set filters
        query.where(buildPredicates(filter, cb, property, availability));

        List<PropertyResponse> properties = em.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .unwrap(org.hibernate.query.Query.class)
                .setTupleTransformer((tuple, aliases) -> {
                    int i = 0;
                    String name =  (String) tuple[i++];
                    PropertyType propertyType = (PropertyType) tuple[i++];
                    String address = (String) tuple[i++];
                    String city = (String) tuple[i++];
                    String country = (String) tuple[i++];
                    BigDecimal price = (BigDecimal) tuple[i++];
                    Boolean available = (Boolean) tuple[i];
                    return new PropertyResponse(
                            name,
                            propertyType.name(),
                            city + ", " + country,
                            country,
                            address,
                            price,
                            available
                    );
                })
                .getResultList();

        return  properties;
    }

    @Override
    public long countPropertiesBy(PropertyFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Property> property = query.from(Property.class);
        Expression<Object> availability = checkAvailability(cb, query, property, filter);
        query.select(cb.count(property)).where(buildPredicates(filter, cb, property, availability));
        return em.createQuery(query).getSingleResult();
    }

    private <T> Expression<Object> checkAvailability(
            CriteriaBuilder cb,
            CriteriaQuery<T> query,
            Root<Property> property,
            PropertyFilter filter
    ) {
        Subquery<Long> reservation = query.subquery(Long.class);
        Root<Reservation> reservationRoot = reservation.from(Reservation.class);
        reservation.select(cb.count(reservationRoot))
                .where(cb.and(
                        cb.equal(reservationRoot.get("property"), property),
                        cb.lessThan(reservationRoot.get("fromDate"), filter.toDate()),
                        cb.greaterThan(reservationRoot.get("toDate"), filter.fromDate())
                ));
        return cb.selectCase()
                .when(cb.greaterThan(reservation, 0L), false)
                .otherwise(true);
    }

    private Predicate[] buildPredicates(
            PropertyFilter filter,
            CriteriaBuilder cb,
            Root<Property> property,
            Expression<Object> availability
    ) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.searchQuery() != null && !filter.searchQuery().isBlank()) {
            predicates.add(
                    cb.or(
                            cb.like(cb.lower(property.get("name")), "%" + filter.searchQuery().toLowerCase() + "%"),
                            cb.like(cb.lower(property.get("address")), "%" + filter.searchQuery().toLowerCase() + "%"),
                            cb.like(cb.lower(property.get("city")), "%" + filter.searchQuery().toLowerCase() + "%"),
                            cb.like(cb.lower(property.get("country")), "%" + filter.searchQuery().toLowerCase() + "%")
                    )
            );
        }
        if (filter.propertyType() != null) {
            predicates.add(cb.equal(property.get("propertyType"), filter.propertyType()));
        }
        if (filter.minPrice() != null && filter.maxPrice() != null) {
            predicates.add(cb.between(property.get("price"), filter.minPrice(), filter.maxPrice()));
        } else if (filter.minPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(property.get("price"), filter.minPrice()));
        } else if (filter.maxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(property.get("price"), filter.maxPrice()));
        }
        if (filter.available() != null && filter.available()) {
            predicates.add(cb.equal(availability, true));
        }
        return predicates.toArray(new Predicate[0]);
    }

}
