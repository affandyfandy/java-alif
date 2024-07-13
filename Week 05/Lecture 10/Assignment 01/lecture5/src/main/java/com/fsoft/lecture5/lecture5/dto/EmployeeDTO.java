package com.fsoft.lecture5.lecture5.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

//@Getter
//@Setter
public class EmployeeDTO {

    private UUID id;

    @NotNull(message = "Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only text")
    private String name;

    private LocalDate dateOfBirth;
    private String address;
    private String department;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\+62[0-9]{9,14}$", message = "Phone must be a valid Indonesian phone number")
    private String phone;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull(message = "Name cannot be null") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only text") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Name cannot be null") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only text") String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^\\+62[0-9]{9,14}$", message = "Phone must be a valid Indonesian phone number") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "^\\+62[0-9]{9,14}$", message = "Phone must be a valid Indonesian phone number") String phone) {
        this.phone = phone;
    }
}
