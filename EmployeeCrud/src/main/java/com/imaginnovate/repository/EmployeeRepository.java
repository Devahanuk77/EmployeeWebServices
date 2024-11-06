package com.imaginnovate.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.imaginnovate.entity.Employee;

@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Employee saveEmployee(Employee emp) {
		String sqlQuery = "INSERT INTO employee( name, email, experience, company)"
				+ "VALUES ( :name,:email,:experience,:company)";

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("id", emp.getId());
		params.addValue("name", emp.getName());
		params.addValue("email", emp.getEmail());
		params.addValue("experience", emp.getExperience());
		params.addValue("company", emp.getCompany());
		namedParameterJdbcTemplate.update(sqlQuery, params);
		String retriveQuery = "SELECT * FROM employee WHERE name =:name AND email=:email AND experience =:experience AND company=:company";
		return namedParameterJdbcTemplate.queryForObject(retriveQuery, params, new EmployeeRowMappper());
	}

	public Employee getEmpById(int id) {
		String sqlQuery = "SELECT * FROM employee where id=:id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		return namedParameterJdbcTemplate.queryForObject(sqlQuery, params, new EmployeeRowMappper());
	}

	public Employee updateEmployee(Employee emp) {
		String sql = "UPDATE employee SET name =:name, email=:email, experience =:experience, company=:company WHERE id =:id";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", emp.getId());
		params.addValue("name", emp.getName());
		params.addValue("email", emp.getEmail());
		params.addValue("experience", emp.getExperience());
		params.addValue("company", emp.getCompany());
		namedParameterJdbcTemplate.update(sql, params);
		String retriveQuery = "SELECT * FROM employee WHERE name =:name AND email=:email AND experience =:experience AND company=:company";
		return namedParameterJdbcTemplate.queryForObject(retriveQuery, params, new EmployeeRowMappper());
	}

	public void deleteEmp(int id) {
		String sqlQuery = "DELETE FROM employee WHERE id =:id";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		namedParameterJdbcTemplate.update(sqlQuery, params);
	}

	public List<Employee> fetchAllEmployees() {
		String sql = "SELECT * FROM employee";
		return namedParameterJdbcTemplate.query(sql, new EmployeeRowMappper());
	}

	public boolean emailExists(String email) {
		String sql = "SELECT COUNT(*) FROM employee WHERE email=:email";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
		return count != null && count > 0;
	}

}
