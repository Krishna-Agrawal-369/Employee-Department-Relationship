package com.springboot.employeeManagement.controller;

import com.springboot.employeeManagement.dto.Employee;
import com.springboot.employeeManagement.dto.Response;
import com.springboot.employeeManagement.kafka.KafkaProducer;
import com.springboot.employeeManagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee/management")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/save/employee")
    public ResponseEntity<Response> saveEmployee(@RequestBody @Valid Employee employee) {
        log.info("EmployeeController :: Save Employee");
        this.employeeService.saveEmployee(employee);
        return new ResponseEntity<>(new Response(true, 0, "Employee record added successfully"), HttpStatus.OK);
    }

    @PostMapping("/save/employees")
    public ResponseEntity<Response> saveEmployees(@RequestBody List<@Valid Employee> employee) {
        log.info("EmployeeController :: Save list of employees");
        this.employeeService.saveEmployees(employee);
        return new ResponseEntity<>(new Response(true, 0, "Employee record added successfully"), HttpStatus.OK);
    }

    @GetMapping("/fetch-all/employee")
    public ResponseEntity<Response> getAllEmployees(@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                    @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                    @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = "AESC", required = false) String sortDir) {
        log.info("EmployeeController :: Get All Employees");
        return new ResponseEntity<>(new Response(true, 0, "Employees records fetched successfully", this.employeeService.getAllEmployees(pageSize, pageNumber, sortBy, sortDir)), HttpStatus.OK);
    }

    @GetMapping("/fetch/employee/{id}")
    public ResponseEntity<Response> getEmployeeById(@PathVariable("id") int id) {
        log.info("EmployeeController :: Get Employee By ID");
        Optional<Employee> employee = this.employeeService.getEmployeeById(id);
        if (employee.isPresent())
            return new ResponseEntity<>(new Response(true, 0, "Employee with id : " + id + " fetched successfully", employee), HttpStatus.OK);
        return new ResponseEntity<>(new Response(false, -1, "No record found with provided id : " + id), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/fetch/employee")
    public ResponseEntity<Response> getEmployeeByName(@RequestParam("name") String employeeName) {
        log.info("EmployeeController :: Get Employee By Name");
        List<Employee> employees = this.employeeService.getEmployeeByName(employeeName);
        if (!employees.isEmpty())
            return new ResponseEntity<>(new Response(true, 0, "Employee with name : " + employeeName + " fetched successfully", employees), HttpStatus.OK);
        return new ResponseEntity<>(new Response(false, -1, "No record found with provided name : " + employeeName), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get/employee")
    public ResponseEntity<Response> getEmployeeByAgeAndDesignation(@RequestParam("age") int employeeAge, @RequestParam("designation") String employeeDesignation) {
        log.info("EmployeeController :: Get Employee By Age and Designation");
        List<Employee> employees = this.employeeService.getEmployeeByAgeAndDesignation(employeeAge, employeeDesignation);
        if (!employees.isEmpty())
            return new ResponseEntity<>(new Response(true, 0, "Employee with age : " + employeeAge + " and designation : " + employeeDesignation + " fetched successfully", employees), HttpStatus.OK);
        return new ResponseEntity<>(new Response(false, -1, "No record found with provided age : " + employeeAge + " and designation : " + employeeDesignation), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/update/employee/{id}")
    public ResponseEntity<Response> updateEmployeeById(@PathVariable("id") int id, @RequestParam String name, @RequestParam String createdBy) {
        log.info("EmployeeController :: Update Employee Name");
        return this.employeeService.updateEmployeeById(id, name, createdBy);
    }


    @PutMapping("/update/employee/{id}")
    public ResponseEntity<Response> updateEmployeeById(@PathVariable("id") int id, @RequestBody Employee employee) {
        log.info("EmployeeController :: Update Employee");
        return this.employeeService.updateEmployeeById(id, employee);
    }

    @DeleteMapping("/delete/employee")
    public ResponseEntity<Response> deleteEmployeeById(@RequestParam int id) {
        log.info("EmployeeController :: Delete Employee");
        return this.employeeService.deleteEmployeeById(id);
    }

    @PostMapping("/message")
    public ResponseEntity<Response> produceMessage(@RequestParam String message) {
        return new ResponseEntity<>(new Response(true, 0, kafkaProducer.show_1(message)), HttpStatus.OK);
    }

}
