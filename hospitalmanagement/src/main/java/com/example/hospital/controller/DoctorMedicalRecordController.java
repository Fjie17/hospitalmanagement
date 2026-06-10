package com.example.hospital.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.MedicalRecord;
import com.example.hospital.entity.Patient;
import com.example.hospital.security.CustomUserDetails;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.service.DoctorService;
import com.example.hospital.service.MedicalRecordService;
import com.example.hospital.service.PatientService;

@Controller
@RequestMapping("/doctor")
public class DoctorMedicalRecordController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private DoctorService doctorService;

	/**
	 * 查看当前医生的所有病历
	 */
	@GetMapping("/records")
	public String viewDoctorMedicalRecords(@AuthenticationPrincipal CustomUserDetails doctorUser, Model model) {
		Long doctorId = doctorUser.getId();

		// 获取医生实体，判断是否存在
		Doctor doctor = doctorService.findById(doctorId).orElse(null);
		if (doctor == null) {
			model.addAttribute("error", "医生信息不存在");
			return "doctor/error_page"; // 你自定义的错误页面
		}

		// 根据 doctorId 查询病历列表
		List<MedicalRecord> records = medicalRecordService.findByDoctorId(doctorId);
		model.addAttribute("records", records);
		return "doctor/medical_records :: content";
	}

	/**
	 * 进入病历填写表单
	 */
	@GetMapping("/records/fill")
	public String fillMedicalRecord(@RequestParam Long patientId, Authentication auth, Model model) {
		Long doctorId = ((CustomUserDetails) auth.getPrincipal()).getId();
		MedicalRecord record = medicalRecordService.findByPatientAndDoctor(patientId, doctorId);

		if (record == null) {
			// 如果之前没有病历，新建一个空的
			record = new MedicalRecord();
		}

		model.addAttribute("record", record);
		model.addAttribute("patientId", patientId);
		return "doctor/fill_record_form :: content";
	}

	/**
	 * 保存病历记录
	 */
	@PostMapping("/records/save")
	@ResponseBody
	public String saveRecord(@RequestParam Long patientId, @RequestParam String diagnosis,
			@RequestParam String treatment, Authentication auth) {
		Long doctorId = ((CustomUserDetails) auth.getPrincipal()).getId();

		MedicalRecord record = medicalRecordService.findByPatientAndDoctor(patientId, doctorId);
		if (record == null) {
			record = new MedicalRecord();
			record.setDoctor(new Doctor(doctorId));
			record.setPatient(new Patient(patientId));
		}

		record.setDiagnosis(diagnosis);
		record.setTreatment(treatment);
		record.setRecordDate(LocalDateTime.now());

		medicalRecordService.save(record);
		return "success";
	}
}