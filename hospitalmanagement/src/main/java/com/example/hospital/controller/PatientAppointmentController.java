package com.example.hospital.controller;

import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.Patient;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PatientAppointmentController {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/patient/appointments")
	public String viewAppointments(Model model, Authentication authentication) {
		if (authentication == null)
			return "redirect:/login";

		String username = authentication.getName();
		Patient patient = patientRepository.findByUsername(username).orElse(null);
		if (patient == null)
			return "redirect:/login";

		List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patient);
		model.addAttribute("appointments", appointments);
		return "patient/appointments :: appointmentList";
	}

}
