package com.unibz.booking.controller;
import com.unibz.booking.dto.BookingDto;
import com.unibz.booking.entity.Booking;
import com.unibz.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBooking(dto));
    }
    @GetMapping
    public ResponseEntity<List<Booking>> list() { return ResponseEntity.ok(service.getAllBookings()); }
    @GetMapping("/{id}")
    public ResponseEntity<Booking> get(@PathVariable Long id) { return ResponseEntity.ok(service.getBookingById(id)); }
    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(
            @PathVariable Long id,
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(service.updateBooking(id, from, to));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.cancelBooking(id); return ResponseEntity.noContent().build(); }
}
