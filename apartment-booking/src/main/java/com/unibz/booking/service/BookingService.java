package com.unibz.booking.service;
import com.unibz.booking.dto.BookingDto;
import com.unibz.booking.entity.Apartment;
import com.unibz.booking.entity.Booking;
import com.unibz.booking.exception.BadRequestException;
import com.unibz.booking.exception.ResourceNotFoundException;
import com.unibz.booking.repository.ApartmentRepository;
import com.unibz.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ApartmentRepository apartmentRepository;

    public BookingService(BookingRepository bookingRepository, ApartmentRepository apartmentRepository) {
        this.bookingRepository = bookingRepository;
        this.apartmentRepository = apartmentRepository;
    }
    public List<Booking> getAllBookings() { return bookingRepository.findAll(); }
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + id));
    }
    public Booking createBooking(BookingDto dto) {
        if (!dto.getEndDate().isAfter(dto.getStartDate())) throw new BadRequestException("endDate must be after startDate");
        Apartment apt = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Apartment not found: " + dto.getApartmentId()));
        if (bookingRepository.hasOverlappingBooking(apt.getId(), dto.getStartDate(), dto.getEndDate(), null)) {
            throw new BadRequestException("Apartment is already booked for the specified dates");
        }
        return bookingRepository.save(new Booking(apt, dto.getStartDate(), dto.getEndDate()));
    }
    public Booking updateBooking(Long id, LocalDate start, LocalDate end) {
        Booking b = getBookingById(id);
        LocalDate s = start != null ? start : b.getStartDate();
        LocalDate e = end != null ? end : b.getEndDate();
        if (!e.isAfter(s)) throw new BadRequestException("endDate must be after startDate");
        if (bookingRepository.hasOverlappingBooking(b.getApartment().getId(), s, e, b.getId())) {
            throw new BadRequestException("Conflict with existing booking");
        }
        b.setStartDate(s);
        b.setEndDate(e);
        return bookingRepository.save(b);
    }
    public void cancelBooking(Long id) { bookingRepository.delete(getBookingById(id)); }
}
