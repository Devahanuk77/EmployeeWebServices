package com.imaginnovate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.imaginnovate.entity.Employee;

public class EmployeeRowMappper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

		Employee emp = new Employee();
		emp.setId(rs.getInt("id"));
		emp.setName(rs.getString("name"));
		emp.setEmail(rs.getString("email"));
		emp.setExperience(rs.getDouble("experience"));
		emp.setCompany(rs.getString("company"));
		return emp;
	}

}
