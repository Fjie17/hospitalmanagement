package com.example.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.repository.DepartmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private DoctorRepository doctorRepo;
	@Autowired
	private PatientRepository patientRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DepartmentRepository departmentRepo;

	// 添加这段
	@GetMapping("")
	public String registerRoot() {
		return "redirect:/register/select";
	}

	@GetMapping("/select")
	public String selectRegister() {
		return "register_select";
	}

	@GetMapping("/doctor")
	public String doctorForm(Model model) {
		model.addAttribute("doctor", new Doctor());
		model.addAttribute("departments", departmentRepo.findAll());
		return "register_doctor";
	}

	@PostMapping("/doctor")
	public String doctorRegister(@ModelAttribute Doctor doctor) {
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
		doctorRepo.save(doctor);
		return "redirect:/register/doctor?success=true"; // 注册成功时附带参数
	}

	@PostMapping("/patient")
	public String patientRegister(@ModelAttribute Patient patient) {
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		patientRepo.save(patient);
		return "redirect:/register/patient?success=true"; // 注册成功时附带参数
	}

	@GetMapping("/patient")
	public String patientForm(Model model) {
		model.addAttribute("patient", new Patient());
		return "register_patient";
	}

}
