package com.kingshuk.springboot.springdatajpaproject.service;

import java.util.List;

import com.kingshuk.springboot.springdatajpaproject.dto.EmployeeDto;
import com.kingshuk.springboot.springdatajpaproject.entities.Employee;



public interface EmployeeManagementService {
	
	Employee addOrUpdateEmployee(Employee employee);
	
	Employee getEmployee(long employeeId);
	
	List<Employee> getAllEmployees();
	
	Employee getEmployeeById(long employeeId);
	
	void deleteEmployee(long employeeId);
	
	void partialUpdateEmployee(EmployeeDto employeeDto);

}
