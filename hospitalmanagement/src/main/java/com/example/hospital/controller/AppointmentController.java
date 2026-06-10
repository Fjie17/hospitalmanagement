package com.example.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.entity.Schedule;
import com.example.hospital.repository.AppointmentRepository;
import com.example.hospital.repository.DepartmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.repository.ScheduleRepository;
import com.example.hospital.service.PatientService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class AppointmentController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private DepartmentRepository departmentRepo;

	@Autowired
	private DoctorRepository doctorRepo;

	@Autowired
	private PatientRepository patientRepo;

	@Autowired
	private AppointmentRepository appointmentRepo;

	@Autowired
	private ScheduleRepository scheduleRepo;

	@GetMapping("/register-department-form")
	public String registerByDepartmentForm(Model model) {
		model.addAttribute("departments", departmentRepo.findAll());
		return "patient/register_by_department";
	}

	@GetMapping("/register-doctor-form")
	public String registerByDoctorForm(Model model) {
		model.addAttribute("doctors", doctorRepo.findAll());
		return "patient/register_by_doctor";
	}

	// 按科室挂号，AJAX接口，返回JSON
	@PostMapping("/register-department")
	@ResponseBody
	public Map<String, Object> registerByDepartment(@RequestParam Long departmentId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime appointmentTime,
			@RequestParam String note, Principal principal) {
		Map<String, Object> result = new HashMap<>();

		Optional<Patient> optionalPatient = patientRepo.findByUsername(principal.getName());
		if (optionalPatient.isEmpty()) {
			result.put("success", false);
			result.put("message", "用户未登录或不存在");
			return result;
		}
		Patient patient = optionalPatient.get();

		List<Doctor> doctors = doctorRepo.findByDepartmentId(departmentId);
		if (doctors.isEmpty()) {
			result.put("success", false);
			result.put("message", "该科室暂无医生");
			return result;
		}
		Doctor doctor = doctors.get(0); // 默认选第一个医生

		Appointment appointment = new Appointment();
		appointment.setPatient(patient);
		appointment.setDoctor(doctor);
		appointment.setAppointmentTime(appointmentTime);
		appointment.setNote(note);
		appointment.setStatus(Appointment.Status.待确认);

		appointmentRepo.save(appointment);

		result.put("success", true);
		return result;
	}

	// 按医生挂号，AJAX接口，返回JSON
	@PostMapping("/register-doctor")
	@ResponseBody
	public Map<String, Object> registerByDoctor(@RequestParam Long doctorId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime appointmentTime,
			@RequestParam String note, Principal principal) {
		Map<String, Object> result = new HashMap<>();

		Optional<Patient> optionalPatient = patientRepo.findByUsername(principal.getName());
		if (optionalPatient.isEmpty()) {
			result.put("success", false);
			result.put("message", "用户未登录或不存在");
			return result;
		}
		Patient patient = optionalPatient.get();

		Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
		if (doctor == null) {
			result.put("success", false);
			result.put("message", "医生不存在");
			return result;
		}

		LocalDate workDate = appointmentTime.toLocalDate();
		Schedule.TimeSlot timeSlot = getTimeSlotEnum(appointmentTime);

		// 查找已有排班
		Optional<Schedule> optionalSchedule = scheduleRepo.findByDoctorIdAndWorkDateAndTimeSlot(doctorId, workDate,
				timeSlot);
		Schedule schedule;

		if (optionalSchedule.isPresent()) {
			schedule = optionalSchedule.get();
			if (schedule.getCurrentPatients() >= schedule.getMaxPatients()) {
				result.put("success", false);
				result.put("message", "该时段已满，请选择其他时间");
				return result;
			}
			schedule.setCurrentPatients(schedule.getCurrentPatients() + 1);
		} else {
			schedule = new Schedule();
			schedule.setDoctor(doctor);
			schedule.setWorkDate(workDate);
			schedule.setTimeSlot(timeSlot);
			schedule.setMaxPatients(10);
			schedule.setCurrentPatients(1);
		}
		scheduleRepo.save(schedule);

		// 保存预约信息
		Appointment appointment = new Appointment();
		appointment.setDoctor(doctor);
		appointment.setPatient(patient);
		appointment.setAppointmentTime(appointmentTime);
		appointment.setStatus(Appointment.Status.待确认);
		appointment.setNote(note);
		appointmentRepo.save(appointment);

		result.put("success", true);
		return result;
	}

	private Schedule.TimeSlot getTimeSlotEnum(LocalDateTime time) {
		int hour = time.getHour();
		if (hour < 12) {
			return Schedule.TimeSlot.上午;
		} else if (hour < 18) {
			return Schedule.TimeSlot.下午;
		} else {
			return Schedule.TimeSlot.晚上;
		}
	}

}
