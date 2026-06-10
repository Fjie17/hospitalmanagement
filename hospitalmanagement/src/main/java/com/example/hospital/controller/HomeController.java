package com.example.hospital.controller;

import com.example.hospital.dto.DoctorDTO;
import com.example.hospital.service.DoctorService;
import com.example.hospital.service.HealthTipService;
import com.example.hospital.entity.HealthTip;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

	private final DoctorService doctorService;
	private final HealthTipService healthTipService;

	public HomeController(DoctorService doctorService, HealthTipService healthTipService) {
		this.doctorService = doctorService;
		this.healthTipService = healthTipService;
	}

	// 首页只显示前5名医生
	@GetMapping("/")
	public String index(Model model) {
		List<DoctorDTO> doctors = doctorService.findAllWithDepartmentName();
		if (doctors.size() > 5) {
			doctors = doctors.subList(0, 5);
		}
		model.addAttribute("doctors", doctors);
		List<HealthTip> healthTips = healthTipService.findAllTips();
		model.addAttribute("healthTips", healthTips);
		return "index";
	}

	// 所有医生列表页
	@GetMapping("/doctor_list")
	public String doctorList(Model model) {
		List<DoctorDTO> doctors = doctorService.findAllWithDepartmentName();
		model.addAttribute("doctors", doctors);
		return "doctor_list";
	}
}
