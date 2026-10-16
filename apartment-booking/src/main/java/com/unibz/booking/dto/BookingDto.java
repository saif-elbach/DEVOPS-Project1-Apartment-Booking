package com.unibz.booking.dto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookingDto {
    @NotNull
    private Long apartmentId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    public BookingDto() {}
    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
