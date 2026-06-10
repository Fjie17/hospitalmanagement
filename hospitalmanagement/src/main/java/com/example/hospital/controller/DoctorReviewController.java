package com.example.hospital.controller;

import com.example.hospital.dto.DoctorReviewRequest;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.DoctorReview;
import com.example.hospital.entity.Patient;
import com.example.hospital.service.DoctorReviewService;
import com.example.hospital.service.DoctorService;
import com.example.hospital.service.PatientService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient/review")
public class DoctorReviewController {

	@Autowired
	private DoctorReviewService doctorReviewService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@GetMapping
	public String reviewPage() {
		// 返回评价页面模板
		return "patient/review";
	}

	@GetMapping("/doctors")
	@ResponseBody
	public List<Doctor> getDoctors() {
		return doctorService.getAllDoctors();
	}

	@GetMapping("/list/{doctorId}")
	@ResponseBody
	public List<DoctorReview> getReviewsByDoctor(@PathVariable Long doctorId) {
		Doctor doctor = doctorService.getDoctorById(doctorId);
		return doctorReviewService.getReviewsByDoctor(doctor);
	}

	// 修改这里，接收 JSON 请求体
	@PostMapping("/add")
	@ResponseBody
	public String addReview(@RequestBody DoctorReviewRequest reviewRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return "请先登录";
		}

		String username = authentication.getName();
		Patient patient = patientService.findByUsername(username);
		if (patient == null) {
			return "请先登录";
		}

		Doctor doctor = doctorService.getDoctorById(reviewRequest.getDoctorId());
		if (doctor == null) {
			return "医生不存在";
		}

		DoctorReview review = new DoctorReview();
		review.setDoctor(doctor);
		review.setPatient(patient);
		review.setRating(reviewRequest.getRating());
		review.setComment(reviewRequest.getComment());

		doctorReviewService.addReview(review);
		return "success";
	}

}
