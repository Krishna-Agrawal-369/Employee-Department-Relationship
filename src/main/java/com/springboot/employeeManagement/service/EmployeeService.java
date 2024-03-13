package com.springboot.employeeManagement.service;

import com.springboot.employeeManagement.dto.Employee;
import com.springboot.employeeManagement.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeService {
    List<Employee> getAllEmployees(Integer pageSize, Integer pageNumber, String sortBy, String sortDir);

    void saveEmployee(Employee employee);

    void saveEmployees(List<Employee> employee);

    public Optional<Employee> getEmployeeById(int id);

    List<Employee> getEmployeeByName(String employeeName);

    List<Employee> getEmployeeByAgeAndDesignation(int age, String designation);

    ResponseEntity<Response> updateEmployeeById(int employeeId, Employee employee);

    ResponseEntity<Response> updateEmployeeById(int employeeId, String employeeName, String createdBy);

    ResponseEntity<Response> deleteEmployeeById(int employeeId);

}
