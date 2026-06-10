package com.example.hospital.controller;

import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Schedule;
import com.example.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@GetMapping("/doctors")
	public String showDoctorList(Model model) {
		List<Doctor> doctors = doctorService.findAll();
		model.addAttribute("doctors", doctors);
		return "doctor_list"; // 对应 doctor_list.html
	}

}
