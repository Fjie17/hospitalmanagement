package com.example.hospital.service.impl;

import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.repository.AppointmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public List<Appointment> getAppointmentsByPatient(Patient patient) {
		return appointmentRepository.findByPatient(patient);
	}

	@Override
	public List<Appointment> findByDoctorIdOrderByAppointmentTime(Long doctorId) {
		return appointmentRepository.findByDoctorIdWithPatient(doctorId);
	}

	@Override
	public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
		Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
		return doctorOpt.map(appointmentRepository::findByDoctorOrderByAppointmentTimeAsc).orElse(List.of());
	}

	@Override
	public void updateAppointmentStatus(Long appointmentId, Appointment.Status status, String note) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new RuntimeException("预约不存在"));
		appointment.setStatus(status);
		appointment.setNote(note);
		appointmentRepository.save(appointment);
	}
}
