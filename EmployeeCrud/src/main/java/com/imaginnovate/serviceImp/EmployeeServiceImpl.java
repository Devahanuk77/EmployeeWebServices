package com.imaginnovate.serviceImp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imaginnovate.dto.EmployeeDTO;
import com.imaginnovate.entity.Employee;
import com.imaginnovate.exception.EmailAlreadyExistsException;
import com.imaginnovate.exception.InValidEmailException;
import com.imaginnovate.repository.EmployeeRepository;
import com.imaginnovate.service.EmployeeService;
import com.imaginnovate.service.KafkaProducerService;
import com.imaginnovate.utils.EmailValidator;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository empRepository;

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Override
	public Employee convertToEmployee(EmployeeDTO dto) {
		Employee emp = new Employee();
		emp.setId(dto.getId());
		emp.setName(dto.getName());
		emp.setEmail(dto.getEmail());
		emp.setExperience(dto.getExperience());
		emp.setCompany(dto.getCompany());
		return emp;
	}

	@Override
	public EmployeeDTO convertToEmployeeDto(Employee emp) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(emp.getId());
		dto.setName(emp.getName());
		dto.setEmail(emp.getEmail());
		dto.setExperience(emp.getExperience());
		dto.setCompany(emp.getCompany());
		return dto;
	}

	@Override
	public EmployeeDTO saveEmployee(EmployeeDTO dto) {
		if (!EmailValidator.isValid(dto.getEmail())) {
			throw new InValidEmailException("Given Email is not valid. Please check the email format.");
		}
		if (empRepository.emailExists(dto.getEmail())) {
			throw new EmailAlreadyExistsException(
					"Given Email is already registered. Please register with another Email.");
		}
		Employee emp = convertToEmployee(dto);
		Employee saveEmployee = empRepository.saveEmployee(emp);
		EmployeeDTO savedEmpDto = convertToEmployeeDto(saveEmployee);
		kafkaProducerService.sendEmployeeRecord(saveEmployee);
		return savedEmpDto;
	}

	@Override
	public EmployeeDTO fetchEmployee(int id) {
		Employee emp = empRepository.getEmpById(id);
		return convertToEmployeeDto(emp);
	}

	@Override
	public void deleteEmployee(int id) {
		empRepository.deleteEmp(id);
		kafkaProducerService.sendEmployeeRecord("Employee with ID " + id + " deleted.");

	}

	@Override
	public EmployeeDTO updateEmployee(EmployeeDTO dto) {
		Employee emp = convertToEmployee(dto);
		Employee updatedEmp = empRepository.updateEmployee(emp);
		EmployeeDTO updatedEmpDto = convertToEmployeeDto(updatedEmp);
		kafkaProducerService.sendEmployeeRecord(updatedEmpDto);
		return updatedEmpDto;
	}

	@Override
	public List<EmployeeDTO> fetchAllEmployees() {
		List<Employee> emp = empRepository.fetchAllEmployees();
		return emp.stream().map(this::convertToEmployeeDto).collect(Collectors.toList());
	}

}
