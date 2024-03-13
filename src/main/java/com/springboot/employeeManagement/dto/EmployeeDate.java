package com.springboot.employeeManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EmployeeDate {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate = LocalDate.now();
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifiedDate = LocalDate.now();
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String createdBy;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String modifiedBy;
}
