package com.fsoft.lecture5.lecture5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only text")
    private String name;

    private LocalDate dateOfBirth;
    private String address;
    private String department;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\+62[0-9]{9,14}$", message = "Phone must be a valid Indonesian phone number")
    private String phone;

    public Employee() {}

    public Employee(UUID id, String name, LocalDate dateOfBirth, String address, String department, String email, String phone) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull(message = "Name is required") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only text") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Name is required") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only text") String name) {
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
