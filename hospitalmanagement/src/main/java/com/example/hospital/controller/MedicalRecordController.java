package com.example.hospital.controller;

import com.example.hospital.entity.MedicalRecord;
import com.example.hospital.entity.Patient;
import com.example.hospital.service.MedicalRecordService;
import com.example.hospital.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MedicalRecordController {

	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private PatientService patientService;

	@GetMapping("/patient/medical-records")
	public String viewMedicalRecords(Authentication authentication, Model model) {
		String username = authentication.getName();
		Patient patient = patientService.findByUsername(username);
		if (patient == null) {
			model.addAttribute("error", "无法找到当前病人信息");
			return "patient/medical_records :: content";
		}
		List<MedicalRecord> records = medicalRecordService.findByPatient(patient);
		model.addAttribute("medicalRecords", records);
		return "patient/medical_records :: content";
	}
}
