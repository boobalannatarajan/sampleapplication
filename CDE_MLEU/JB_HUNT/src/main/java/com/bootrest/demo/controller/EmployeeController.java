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
		return "Welcome to JB HUNT community practise session";
    }
	@RequestMapping("/jbHuntEmpDetails")
    public List<Employee> getEmployees() 
    {
		List<Employee> employeesList = new ArrayList<Employee>();
		employeesList.add(new Employee(712231,"krishna","moorthy","krishna.moorthy@cognizant.com"));
		employeesList.add(new Employee(916220,"senthil","murugan","senthil.murugan@cognizant.com"));
		return employeesList;
    }
}

