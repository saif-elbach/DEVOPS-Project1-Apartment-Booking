package com.unibz.booking.controller;
import com.unibz.booking.dto.ApartmentDto;
import com.unibz.booking.entity.Apartment;
import com.unibz.booking.service.ApartmentService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {
    private final ApartmentService service;
    public ApartmentController(ApartmentService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Apartment> create(@Valid @RequestBody ApartmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createApartment(dto));
    }
    @GetMapping
    public ResponseEntity<List<Apartment>> list() { return ResponseEntity.ok(service.getAllApartments()); }
    @GetMapping("/{id}")
    public ResponseEntity<Apartment> get(@PathVariable Long id) { return ResponseEntity.ok(service.getApartmentById(id)); }
    @PutMapping("/{id}")
    public ResponseEntity<Apartment> update(@PathVariable Long id, @RequestBody ApartmentDto dto) {
        return ResponseEntity.ok(service.updateApartment(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.deleteApartment(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/search")
    public ResponseEntity<List<Apartment>> search(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(service.searchAvailableApartments(from, to));
    }
}
