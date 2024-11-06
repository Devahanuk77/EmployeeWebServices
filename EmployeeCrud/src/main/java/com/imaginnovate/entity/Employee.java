package com.imaginnovate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double experience;
	private String company;
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Employee(int id, String name, double experience, String company, String email) {
		this.id = id;
		this.name = name;
		this.experience = experience;
		this.company = company;
		this.email = email;
	}

	public Employee() {
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", experience=" + experience + ", company="
				+ company + ", email=" + email + "]";
	}

}
