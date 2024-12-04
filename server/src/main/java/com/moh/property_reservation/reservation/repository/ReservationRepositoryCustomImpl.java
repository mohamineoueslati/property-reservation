package com.moh.property_reservation.reservation.repository;

import com.moh.property_reservation.property.domain.Property;
import com.moh.property_reservation.property.domain.PropertyType;
import com.moh.property_reservation.reservation.domain.Reservation;
import com.moh.property_reservation.reservation.dto.ReservationFilter;
import com.moh.property_reservation.reservation.dto.ReservationResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("all")
    public List<ReservationResponse> findReservationsBy(ReservationFilter filter, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<Reservation> reservation = query.from(Reservation.class);

        Path<Property> property = reservation.get("property");

        // Select fields
        query.multiselect(
                property.get("name").alias("name"),
                property.get("propertyType").alias("propertyType"),
                property.get("address").alias("address"),
                property.get("city").alias("city"),
                property.get("country").alias("country"),
                reservation.get("moneySpent").alias("moneySpent"),
                reservation.get("fromDate").alias("fromDate"),
                reservation.get("toDate").alias("toDate")
        );

        query.where(buildPredicates(filter, reservation, property, cb));

        List<ReservationResponse> reservations = em.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .unwrap(Query.class)
                .setTupleTransformer((tuple, aliases) -> {
                    int i = 0;
                    String name =  (String) tuple[i++];
                    PropertyType propertyType = (PropertyType) tuple[i++];
                    String address = (String) tuple[i++];
                    String city = (String) tuple[i++];
                    String country = (String) tuple[i++];
                    BigDecimal moneySpent = (BigDecimal) tuple[i++];
                    OffsetDateTime fromDate = (OffsetDateTime) tuple[i++];
                    OffsetDateTime toDate = (OffsetDateTime) tuple[i++];
                    return new ReservationResponse(
                            name,
                            propertyType.name(),
                            city + ", " + country,
                            country,
                            address,
                            moneySpent,
                            fromDate,
                            toDate
                    );
                })
                .getResultList();

        return reservations;
    }

    @Override
    public long countReservationsBy(ReservationFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Reservation> reservation = query.from(Reservation.class);
        Path<Property> property = reservation.get("property");
        query.select(cb.count(reservation)).where(buildPredicates(filter, reservation, property, cb));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    public BigDecimal sumTotalMoneySpentOnFlat() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> moneySpentOnFlatsQuery = cb.createQuery(BigDecimal.class);
        Root<Reservation> reservation = moneySpentOnFlatsQuery.from(Reservation.class);
        Path<Property> property = reservation.get("property");
        moneySpentOnFlatsQuery.select(cb.sum(reservation.get("moneySpent")))
                .where(cb.equal(property.get("propertyType"), PropertyType.FLAT));
        return em.createQuery(moneySpentOnFlatsQuery).getSingleResult();
    }

    @Override
    public BigDecimal sumTotalMoneySpentOnHotel() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> moneySpentOnHotelsQuery = cb.createQuery(BigDecimal.class);
        Root<Reservation> reservation = moneySpentOnHotelsQuery.from(Reservation.class);
        Path<Property> property = reservation.get("property");
        moneySpentOnHotelsQuery.select(cb.sum(reservation.get("moneySpent")))
                .where(cb.equal(property.get("propertyType"), PropertyType.HOTEL_ROOM));
        return em.createQuery(moneySpentOnHotelsQuery).getSingleResult();
    }

    @Override
    public String cityWithMostReservations() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cityWithMostReservationsQuery = cb.createQuery(Object[].class);
        Root<Reservation> reservation = cityWithMostReservationsQuery.from(Reservation.class);
        Path<Property> property = reservation.get("property");
        cityWithMostReservationsQuery.multiselect(property.get("city"), cb.count(reservation))
                .groupBy(property.get("city"))
                .orderBy(cb.desc(cb.count(reservation)));

        List<Object[]> result = em.createQuery(cityWithMostReservationsQuery)
                .setMaxResults(1)
                .getResultList();

        return result.isEmpty() ? null : result.getFirst()[0].toString();
    }

    private Predicate[] buildPredicates(
            ReservationFilter filter,
            Root<Reservation> reservation,
            Path<Property> property,
            CriteriaBuilder cb
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
            predicates.add(cb.between(reservation.get("moneySpent"), filter.minPrice(), filter.maxPrice()));
        } else if (filter.minPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(reservation.get("moneySpent"), filter.minPrice()));
        } else if (filter.maxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(reservation.get("moneySpent"), filter.maxPrice()));
        }
        if (filter.fromDate() != null && filter.toDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(reservation.get("fromDate"), filter.fromDate()));
        }
        if (filter.toDate() != null && filter.fromDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(reservation.get("toDate"), filter.toDate()));
        }
        return predicates.toArray(new Predicate[0]);
    }
}
