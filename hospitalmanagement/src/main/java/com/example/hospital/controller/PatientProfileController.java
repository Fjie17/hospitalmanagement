package com.example.hospital.controller;

import com.example.hospital.entity.Patient;
import com.example.hospital.repository.PatientRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientProfileController {

	@Autowired
	private PatientRepository patientRepo;

	// 注入密码加密器
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping("/profile")
	public String patientProfile(Model model, Principal principal) {
		Optional<Patient> optionalPatient = patientRepo.findByUsername(principal.getName());
		if (optionalPatient.isPresent()) {
			model.addAttribute("patient", optionalPatient.get());
			return "patient/patient_profile";
		} else {
			return "error/403";
		}
	}

	@PostMapping("/profile/save")
	@ResponseBody
	public String saveProfile(@RequestParam("phone") String phone,
			@RequestParam(value = "password", required = false) String password, Principal principal) {
		Optional<Patient> optionalPatient = patientRepo.findByUsername(principal.getName());
		if (optionalPatient.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "无权限访问");
		}

		Patient patient = optionalPatient.get();

		patient.setPhone(phone);

		if (password != null && !password.isBlank()) {
			String encodedPwd = passwordEncoder.encode(password);
			patient.setPassword(encodedPwd);
		}

		patientRepo.save(patient);
		return "ok";
	}
}