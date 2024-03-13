package com.springboot.employeeManagement.service;

import com.springboot.employeeManagement.dto.Employee;
import com.springboot.employeeManagement.dto.Response;
import com.springboot.employeeManagement.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @CachePut(cacheNames = "employee", key = "#employee")
    public void saveEmployee(Employee employee) {
        log.info("EmployeeService :: Save Employee");
        employee.setModifiedBy(employee.getCreatedBy());
        this.employeeRepository.save(employee);
    }

    @Override
    @CachePut(cacheNames = "employee")
    public void saveEmployees(List<Employee> employees) {
        log.info("EmployeeService :: Save list of employees");
        employees = employees.stream().map(e -> {
            e.setModifiedBy(e.getCreatedBy());
            return e;
        }).collect(Collectors.toList());
        this.employeeRepository.saveAll(employees);
    }

    @Override
    @CachePut(cacheNames = "employee", key = "#id")
    public Optional<Employee> getEmployeeById(int id) {
        log.info("EmployeeService :: Get Employee By ID");
        return this.employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getEmployeeByName(String employeeName) {
        log.info("EmployeeService :: Get Employee By Name");
        return this.employeeRepository.findByName(employeeName);
    }

    @Override
    public List<Employee> getEmployeeByAgeAndDesignation(int age, String designation) {
        log.info("EmployeeService :: Get Employee By Age and Designation");
        return this.employeeRepository.findByAgeAndDesignation(age, designation);
    }

    @Override
    @CachePut(cacheNames = "employee")
    public List<Employee> getAllEmployees(Integer pageSize, Integer pageNumber, String sortBy, String sortDir) {
        log.info("EmployeeService :: Get All Employees");
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return this.employeeRepository.findAll(pageable).getContent();
    }

    @Override
    public ResponseEntity<Response> updateEmployeeById(int id, Employee employee) {
        log.info("EmployeeService :: Update Employee");
        Optional<Employee> oldEmployee = this.employeeRepository.findById(id);
        if (oldEmployee.isPresent()) {
            oldEmployee.get().setModifiedDate(LocalDate.now().plusDays(1));
            oldEmployee.get().setModifiedBy(employee.getCreatedBy());
            if (Objects.nonNull(employee.getName()))
                oldEmployee.get().setName(employee.getName());
            if (employee.getSalary() != 0)
                oldEmployee.get().setSalary(employee.getSalary());
            if (employee.getAge() != 0)
                oldEmployee.get().setAge(employee.getAge());
            if (employee.getExperience() != 0)
                oldEmployee.get().setExperience(employee.getExperience());
            if (Objects.nonNull(employee.getDesignation()))
                oldEmployee.get().setDesignation(employee.getDesignation());
            if (Objects.nonNull(employee.getLaptop()))
                oldEmployee.get().setLaptop(employee.getLaptop());
            if (Objects.nonNull(employee.getDepartments()))
                oldEmployee.get().setDepartments(employee.getDepartments());
            this.employeeRepository.save(oldEmployee.get());
            return new ResponseEntity<>(new Response(true, 0, "Employee Record with ID : " + id + " updated successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, -1, "Employee with id : " + id + " not present in records"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Response> updateEmployeeById(int id, String name, String createdBy) {
        log.info("EmployeeService :: Update Employee Name");
        Optional<Employee> oldEmployee = this.employeeRepository.findById(id);
        if (oldEmployee.isPresent()) {
            Employee employee = oldEmployee.get();
            employee.setName(name);
            employee.setModifiedDate(LocalDate.now().plusDays(1));
            employee.setModifiedBy(createdBy);
            this.employeeRepository.save(employee);
            return new ResponseEntity<>(new Response(true, 0, "Employee Record with ID : " + id + " updated successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, -1, "Employee Record with ID : " + id + " not present in records"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @CacheEvict(value = "employee", key = "#id")
    public ResponseEntity<Response> deleteEmployeeById(int id) {
        log.info("EmployeeService :: Delete Employee");
        List<Employee> employeeList = this.employeeRepository.findAll();
        boolean isPresent = employeeList.stream().anyMatch(i -> i.getId() == id);
        if (isPresent) {
            this.employeeRepository.deleteById(id);
            return new ResponseEntity<>(new Response(true, 0, "Employee with ID : " + id + " deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, -1, "Employee with ID : " + id + " not present in records"), HttpStatus.BAD_REQUEST);
        }
    }
}
