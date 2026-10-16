package com.unibz.booking.repository;
import com.unibz.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
           "WHERE b.apartment.id = :apartmentId " +
           "AND b.startDate < :endDate AND b.endDate > :startDate " +
           "AND (:excludeBookingId IS NULL OR b.id != :excludeBookingId)")
    boolean hasOverlappingBooking(@Param("apartmentId") Long apartmentId,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate,
                                  @Param("excludeBookingId") Long excludeBookingId);
}
