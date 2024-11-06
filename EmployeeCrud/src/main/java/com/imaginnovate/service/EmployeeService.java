package com.imaginnovate.service;

import java.util.List;

import com.imaginnovate.dto.EmployeeDTO;
import com.imaginnovate.entity.Employee;

public interface EmployeeService {
	
	EmployeeDTO saveEmployee(EmployeeDTO dto);
	EmployeeDTO fetchEmployee(int id);
	void deleteEmployee(int id);
	EmployeeDTO updateEmployee(EmployeeDTO dto);
	List<EmployeeDTO> fetchAllEmployees();
	Employee convertToEmployee(EmployeeDTO dto);
	EmployeeDTO convertToEmployeeDto(Employee emp);

}
