package com.springboot.employeeManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@SuperBuilder
public class Employee extends EmployeeDate implements Serializable {
    @Serial
    private static final long serialVersionUID = 2322803002550245166L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @NotEmpty(message = "Name cannot be null and must have size greater than 0")
    @Pattern(regexp = "^[a-zA-Z\s]{3,255}$", message = "Input string length must be greater than 3 and string must be alphabetic only")
    private String name;
    @Min(100000)
    @Max(1000000000)
    private long salary;
    @NotEmpty(message = "Designation cannot be null and must have size greater than 0")
    @Pattern(regexp = "^[A-Z]{2,6}$", message = "length must be between 2 and 6 and should be uppercase")
    private String designation;
    @NotNull(message = "Date of joining is required")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate doj = LocalDate.now();
    @Min(value = 18)
    @Max(value = 60)
    private int age;
    @Min(value = 0)
    @Max(value = 40)
    private int experience;
    @Email
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private Laptop laptop;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments;
}