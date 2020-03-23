package com.kingshuk.springboot.springdatajpaproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kingshuk.springboot.springdatajpaproject.dto.EmployeeDto;
import com.kingshuk.springboot.springdatajpaproject.entities.Employee;
import com.kingshuk.springboot.springdatajpaproject.exceptionhandling.EmployeeNotFoundException;
import com.kingshuk.springboot.springdatajpaproject.service.EmployeeManagementService;

@RestController
@RequestMapping("/employees")
public class EmployeeManagementController {

	@Autowired
	private DozerBeanMapper myBeanMapper;

	@Autowired
	private EmployeeManagementService employeeManagementService;


	@PostMapping(path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employee) {

		Employee employee2 = myBeanMapper.map(employee, Employee.class);

		return ResponseEntity
				.ok(myBeanMapper.map(employeeManagementService.addOrUpdateEmployee(employee2), EmployeeDto.class));

	}

	@GetMapping(path = "/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
		List<EmployeeDto> employeeList = new ArrayList<>();

		myBeanMapper.map(employeeManagementService.getAllEmployees(), employeeList);

		if (employeeList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(employeeList);
	}

	@GetMapping(path = "/{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EmployeeDto> getEmployeeByEmployeeId(@PathVariable("employeeId") Long empId) {

		Employee employee = employeeManagementService.getEmployeeById(empId);

		/*
		 * Just commenting it out so that we can test the exception handling for this
		 * REST service
		 * 
		 * if (employee == null) { return ResponseEntity.noContent().build(); } else {
		 * return ResponseEntity.ok(myBeanMapper.map(employee, EmployeeDto.class)); }
		 */

		// The below code is just for checking out the exception handling part

		if (employee == null) {
			throw new EmployeeNotFoundException();
		}
		
		return ResponseEntity.ok(myBeanMapper.map(employee, EmployeeDto.class));

	}

	@PutMapping(path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employee) {

		Employee employee2 = myBeanMapper.map(employee, Employee.class);

		return ResponseEntity
				.ok(myBeanMapper.map(employeeManagementService.addOrUpdateEmployee(employee2), EmployeeDto.class));

	}

	@PatchMapping(path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> updatePartialEmployeeDto(@RequestBody EmployeeDto employeeDto) {
		employeeManagementService.partialUpdateEmployee(employeeDto);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping(path = "/{employeeId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long empId) {
		employeeManagementService.deleteEmployee(empId);

		return ResponseEntity.ok().build();
	}

}
