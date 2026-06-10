package com.example.hospital.dto;

public class DoctorDTO {
	private String name;
	private String phone;
	private String title;
	private String departmentName;
	private String gender; // 新增

	public DoctorDTO(String name, String phone, String title, String departmentName, String gender) {
		this.name = name;
		this.phone = phone;
		this.title = title;
		this.departmentName = departmentName;
		this.gender = gender;
	}

	// Getter 和 Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
