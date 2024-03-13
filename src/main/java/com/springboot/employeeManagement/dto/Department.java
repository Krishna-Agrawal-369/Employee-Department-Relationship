package com.springboot.employeeManagement.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Department implements Serializable {
    @Serial
    private static final long serialVersionUID = 6140783737348213215L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int departmentId;
    private String departmentName;
    private List<String> techStack;
}
