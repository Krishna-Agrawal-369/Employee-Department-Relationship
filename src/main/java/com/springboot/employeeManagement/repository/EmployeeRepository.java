package com.springboot.employeeManagement.repository;

import com.springboot.employeeManagement.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query(value = "select * from employee e where e.name = :name", nativeQuery = true)
    List<Employee> findByName(String name);

    @Query(value = "select * from employee e where e.age = :age and e.designation = :designation", nativeQuery = true)
    List<Employee> findByAgeAndDesignation(int age, String designation);
}
