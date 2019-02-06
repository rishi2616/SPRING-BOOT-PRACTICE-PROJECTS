package com.kingshuk.springboot.springdatajpaproject;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kingshuk.springboot.springdatajpaproject.controller.EmployeeManagementController;
import com.kingshuk.springboot.springdatajpaproject.dto.AddressDto;
import com.kingshuk.springboot.springdatajpaproject.dto.DepartmentDto;
import com.kingshuk.springboot.springdatajpaproject.dto.EmployeeDto;
import com.kingshuk.springboot.springdatajpaproject.entities.Address;
import com.kingshuk.springboot.springdatajpaproject.entities.Department;
import com.kingshuk.springboot.springdatajpaproject.entities.Employee;
import com.kingshuk.springboot.springdatajpaproject.service.EmployeeManagementService;

/**
 * What this class is doing here is performing unit testing, not end to end 
 * integration test and that's exactly why we have mocked out the employee management service class
 * @author kingshuksmacbookpro
 *
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {EmployeeManagementController.class})
@WebMvcTest(value=EmployeeManagementController.class)
public class SpringbootEmployeeProjectApplicationTests {
	
	@Autowired
	MockMvc mockMvc;

	@MockBean
	EmployeeManagementService employeeService;
	
	
	List<EmployeeDto> employeeDtos;
	
	List<Employee> employees;
	
	Employee employee;
	
	@PostConstruct
	public void loadDataForTesting() {
		AddressDto address = new AddressDto();
		address.setAddressId(9L);
		address.setAddressLine1("771 Shady Grove Ln");
		address.setAddressLine2("");
		address.setCity("Buffalo Grove");
		address.setState("IL");
		address.setZipCode("60089");
		
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentId(9l);
		departmentDto.setDepartmentName("IT");
		
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmployeeId(9l);
		employeeDto.setFirstName("Kingshuk");
		employeeDto.setLastName("Mukherjee");
		employeeDto.setAddress(address);
		employeeDto.setDepartment(departmentDto);
		
		employeeDtos = Arrays.asList(employeeDto);
		
		employee = new Employee(employeeDto, new Address(address), new Department(departmentDto));
		
		employees = Arrays.asList(employee);
	}
	
	

	@Test
	public void testGetRequestForAllEmployees() throws Exception {
		
		String expectedResult =  "[{\"employeeId\":1,\"firstName\":\"Kingshuk\",\"lastName\":\"Mukherjee\",\"department\":{\"departmentId\":1,\"departmentName\":\"IT\"},\"address\":{\"addressId\":1,\"addressLine1\":\"771 Shady Grove Ln\",\"addressLine2\":\"\",\"city\":\"Buffalo Grove\",\"state\":\"IL\",\"zipCode\":\"60089\"}}]";
		
		Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees")
								.accept(MediaType.APPLICATION_JSON_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		JSONAssert.assertEquals(expectedResult, result.getResponse().getContentAsString(), true);
	}

}

