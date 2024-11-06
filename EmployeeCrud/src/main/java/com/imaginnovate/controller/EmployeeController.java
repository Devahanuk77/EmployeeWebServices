package com.imaginnovate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.dto.EmployeeDTO;
import com.imaginnovate.service.EmployeeService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/saveEmp")
	public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDTO dto) {
		employeeService.saveEmployee(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Employee created Successfully");
	}

	@GetMapping("/fetchEmp/{id}")
	public ResponseEntity<EmployeeDTO> fetchEmpById(@PathVariable int id) {
		EmployeeDTO dto = employeeService.fetchEmployee(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/fetchAll")
	public ResponseEntity<List<EmployeeDTO>> getAllEmps() {
		List<EmployeeDTO> empList = employeeService.fetchAllEmployees();
		return ResponseEntity.ok(empList);
	}

	@DeleteMapping("deleteEmp/{id}")
	public ResponseEntity<String> deleteEmpById(@PathVariable int id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.ok("Employee Id " + id + " has been deleted successfully");
	}
	@PutMapping("/updateEmp/{id}")
	public ResponseEntity<String> updatEmployee(@PathVariable int id, @RequestBody EmployeeDTO dto) {
		employeeService.updateEmployee(dto);
		return ResponseEntity.ok("Emp with id " + id + " has been updated successfully");
	}

}
