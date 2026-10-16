package com.unibz.booking.repository;
import com.unibz.booking.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    @Query("SELECT a FROM Apartment a WHERE a.id NOT IN (" +
           "  SELECT b.apartment.id FROM Booking b " +
           "  WHERE b.startDate < :endDate AND b.endDate > :startDate" +
           ")")
    List<Apartment> findAvailableApartments(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
