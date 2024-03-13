package com.springboot.employeeManagement;

import com.springboot.employeeManagement.service.EmployeeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeManagementApplicationTests {

	@Autowired
	public EmployeeService employeeService;


	@BeforeAll
	public static void beforeTest() {
		System.out.println("------------------------Before Test------------------------------------------------------");
	}

	@Test
	@Timeout(9)
	public void solve() throws InterruptedException {
		Assertions.assertEquals("Krishna", "Krishna", "Test1 : ");
//		Thread.sleep(12000);
		Assertions.assertFalse(employeeService.getEmployeeById(1).isEmpty(),"Employee by ID Response is empty");
		System.out.println("Test Executed successfully");
	}

	@AfterAll
	public static void afterTest() {
		System.out.println("------------------------After Test------------------------------------------------------");
	}

}
