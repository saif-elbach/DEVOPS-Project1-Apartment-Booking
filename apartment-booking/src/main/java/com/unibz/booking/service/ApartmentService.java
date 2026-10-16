package com.unibz.booking.service;
import com.unibz.booking.dto.ApartmentDto;
import com.unibz.booking.entity.Apartment;
import com.unibz.booking.exception.BadRequestException;
import com.unibz.booking.exception.ResourceNotFoundException;
import com.unibz.booking.repository.ApartmentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    public ApartmentService(ApartmentRepository apartmentRepository) { this.apartmentRepository = apartmentRepository; }

    public List<Apartment> getAllApartments() { return apartmentRepository.findAll(); }
    public Apartment getApartmentById(Long id) {
        return apartmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Apartment not found with id: " + id));
    }
    public Apartment createApartment(ApartmentDto dto) {
        return apartmentRepository.save(new Apartment(dto.getName(), dto.getAddress()));
    }
    public Apartment updateApartment(Long id, ApartmentDto dto) {
        Apartment apt = getApartmentById(id);
        if (dto.getName() != null) apt.setName(dto.getName());
        if (dto.getAddress() != null) apt.setAddress(dto.getAddress());
        return apartmentRepository.save(apt);
    }
    public void deleteApartment(Long id) { apartmentRepository.delete(getApartmentById(id)); }
    public List<Apartment> searchAvailableApartments(LocalDate from, LocalDate to) {
        if (!to.isAfter(from)) throw new BadRequestException("'to' date must be after 'from' date");
        return apartmentRepository.findAvailableApartments(from, to);
    }
}
