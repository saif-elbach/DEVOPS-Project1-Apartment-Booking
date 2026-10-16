package com.unibz.booking.dto;
import jakarta.validation.constraints.NotBlank;

public class ApartmentDto {
    @NotBlank
    private String name;
    @NotBlank
    private String address;

    public ApartmentDto() {}
    public ApartmentDto(String name, String address) { this.name = name; this.address = address; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
