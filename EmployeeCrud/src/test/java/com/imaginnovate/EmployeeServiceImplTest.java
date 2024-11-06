package com.imaginnovate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.imaginnovate.dto.EmployeeDTO;
import com.imaginnovate.entity.Employee;
import com.imaginnovate.repository.EmployeeRepository;
import com.imaginnovate.service.KafkaProducerService;
import com.imaginnovate.serviceImp.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository empRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO employeeDto;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employeeDto = new EmployeeDTO();
        employeeDto.setId(1);
        employeeDto.setEmail("jaggu123@gmail.com");
        employeeDto.setName("Jagadeesh");
        employeeDto.setExperience(2.8);
        employeeDto.setCompany("Tesla");

        employee = new Employee();
        employee.setId(1);
        employee.setEmail("jaggu123@gmail.com");
        employee.setName("Jagadeesh");
        employee.setExperience(2.8);
        employee.setCompany("Tesla");
    }

    @Test
    public void testSaveEmployee() {
        when(empRepository.saveEmployee(any(Employee.class))).thenReturn(employee);
        when(empRepository.emailExists(employeeDto.getEmail())).thenReturn(false);

        EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDto);

        assertNotNull(savedEmployee);
        assertEquals("jaggu123@gmail.com", savedEmployee.getEmail());
        verify(kafkaProducerService).sendEmployeeRecord(any(Employee.class));
    }

    @Test
    public void testFetchEmployee() {
        when(empRepository.getEmpById(1)).thenReturn(employee);

        EmployeeDTO fetchedEmployee = employeeService.fetchEmployee(1);

        assertNotNull(fetchedEmployee);
        assertEquals(1, fetchedEmployee.getId());
    }

    @Test
    public void testDeleteEmployee() {
        doNothing().when(empRepository).deleteEmp(1);

        employeeService.deleteEmployee(1);

        verify(empRepository).deleteEmp(1);
        verify(kafkaProducerService).sendEmployeeRecord("Employee with ID " + 1 + " deleted.");
    }

    @Test
    public void testUpdateEmployee() {
        when(empRepository.updateEmployee(any(Employee.class))).thenReturn(employee);
       lenient().when(empRepository.emailExists(employeeDto.getEmail())).thenReturn(true);

        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDto);

        assertNotNull(updatedEmployee);
        assertEquals("jaggu123@gmail.com", updatedEmployee.getEmail());
        verify(kafkaProducerService).sendEmployeeRecord(updatedEmployee);
    }

    @Test
    public void testFetchAllEmployees() {
        when(empRepository.fetchAllEmployees()).thenReturn(Collections.singletonList(employee));

        List<EmployeeDTO> employees = employeeService.fetchAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
    }
} 