package com.example.hospital.controller;

import com.example.hospital.dto.AppointmentDTO;
import com.example.hospital.entity.Appointment;
import com.example.hospital.security.CustomUserDetails;
import com.example.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doctor")
public class DoctorAppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/appointments")
	public String viewAppointments(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		Long doctorId = userDetails.getId();
		List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctor(doctorId).stream()
				.map(AppointmentDTO::new).toList();

		model.addAttribute("appointments", appointments);
		return "doctor/appointments";
	}

	@PostMapping("/appointments/update")
	@ResponseBody
	public String updateAppointmentStatus(@RequestParam Long appointmentId, @RequestParam String status,
			@RequestParam(required = false) String note) {
		Appointment.Status statusEnum = Appointment.Status.valueOf(status);
		appointmentService.updateAppointmentStatus(appointmentId, statusEnum, note);
		return "success";
	}
}
