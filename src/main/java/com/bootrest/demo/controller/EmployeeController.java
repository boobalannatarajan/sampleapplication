package com.bootrest.demo.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootrest.demo.model.Employee;

@RestController
public class EmployeeController {
	
	@RequestMapping("/")
    public String getWelcomeNote() 
    {
		return "Welcome to CDE ML community practise session";
    }
	@RequestMapping("/empDetails")
    public List<Employee> getEmployees() 
    {
		List<Employee> employeesList = new ArrayList<Employee>();
		employeesList.add(new Employee(716631,"boobalan","natarajan","boobalan.natarajan@cognizant.com"));
		employeesList.add(new Employee(916000,"siva","balan","siva.balan@cognizant.com"));
		return employeesList;
    }
}

