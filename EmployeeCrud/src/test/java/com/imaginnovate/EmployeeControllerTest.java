package com.imaginnovate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginnovate.controller.EmployeeController;
import com.imaginnovate.dto.EmployeeDTO;
import com.imaginnovate.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1);
        employeeDTO.setName("Ganesh");
        employeeDTO.setEmail("ganu12@gmail.com");
        employeeDTO.setCompany("TCS");
        employeeDTO.setExperience(4.2);
    }

    @Test
    void testSaveEmployee() throws Exception {
        when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/emp/saveEmp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Employee created Successfully"));

        verify(employeeService, times(1)).saveEmployee(any(EmployeeDTO.class));
    }

    @Test
    void testFetchEmployeeById() throws Exception {
        when(employeeService.fetchEmployee(1)).thenReturn(employeeDTO);

        mockMvc.perform(get("/emp/fetchEmp/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ganesh"))
                .andExpect(jsonPath("$.email").value("ganu12@gmail.com"))
                .andExpect(jsonPath("$.company").value("TCS"))
                .andExpect(jsonPath("$.experience").value("4.2"));

        verify(employeeService, times(1)).fetchEmployee(1);
    }

    @Test
    void testFetchAllEmployees() throws Exception {
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO);
        when(employeeService.fetchAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/emp/fetchAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ganesh"));

        verify(employeeService, times(1)).fetchAllEmployees();
    }

    @Test
    void testDeleteEmployeeById() throws Exception {
        mockMvc.perform(delete("/emp/deleteEmp/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee Id 1 has been deleted successfully"));

        verify(employeeService, times(1)).deleteEmployee(1);
    }

    @Test
    void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(put("/emp/updateEmp/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Emp with id 1 has been updated successfully"));

        verify(employeeService, times(1)).updateEmployee(any(EmployeeDTO.class));
    }
} 