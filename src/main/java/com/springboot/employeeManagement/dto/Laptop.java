package com.springboot.employeeManagement.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Laptop implements Serializable {
    @Serial
    private static final long serialVersionUID = 4752193795688756644L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int laptopId;
    private String laptopName;
}
