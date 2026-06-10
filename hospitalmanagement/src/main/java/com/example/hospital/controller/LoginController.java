package com.example.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.hospital.entity.Admin;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.service.PatientService;
import com.example.hospital.service.AdminService;
import com.example.hospital.service.DoctorService;

@Controller
public class LoginController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private AdminService adminService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/default")
	public String redirectAfterLogin(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/login";
		}
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
			return "redirect:/admin/index";
		else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR")))
			return "redirect:/doctor/index";
		else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT")))
			return "redirect:/patient/index";
		return "redirect:/login";
	}

	@GetMapping("/admin/index")
	public String adminHome(Authentication authentication, Model model) {
		String username = authentication.getName();
		Admin admin = adminService.findByUsername(username);
		model.addAttribute("admin", admin);
		return "admin/index";
	}

	@GetMapping("/doctor/index")
	public String doctorHome(Authentication authentication, Model model) {
		String username = authentication.getName();
		Doctor doctor = doctorService.findByUsername(username);
		model.addAttribute("doctor", doctor);
		return "doctor/index";
	}

	@GetMapping("/patient/index")
	public String patientHome(Authentication authentication, Model model) {
		String username = authentication.getName(); // 当前登录用户名
		Patient patient = patientService.findByUsername(username);
		model.addAttribute("patient", patient);
		return "patient/index";
	}
}
