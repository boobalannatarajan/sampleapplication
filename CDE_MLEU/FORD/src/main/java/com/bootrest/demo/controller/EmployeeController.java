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
		return "Welcome to FORD community practise session";
    }
	@RequestMapping("/fordEmpDetails")
    public List<Employee> getEmployees() 
    {
		List<Employee> employeesList = new ArrayList<Employee>();
		employeesList.add(new Employee(711131,"vinay","kumar","vinay.kumar@cognizant.com"));
		employeesList.add(new Employee(911100,"veera","sekaran","veera.sekaran@cognizant.com"));
		return employeesList;
    }
}

