package com.bootrest.demo.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bootrest.demo.model.Employee;

@RestController
@RequestMapping("/restAPI/")
public class EmployeeController {
	
	@RequestMapping("/welcomeNote")
    public String getWelcomeNote() 
    {
		return "Welcome to CDE ML community practise session";
    }
	@RequestMapping(value="empDetails", method=RequestMethod.GET)
    public List<Employee> getEmployees() 
    {
		List<Employee> employeesList = new ArrayList<Employee>();
		employeesList.add(new Employee(716631,"boobalan","natarajan","boobalan.natarajan@cognizant.com"));
		employeesList.add(new Employee(916000,"siva","balan","siva.balan@cognizant.com"));
		return employeesList;
    }
}

