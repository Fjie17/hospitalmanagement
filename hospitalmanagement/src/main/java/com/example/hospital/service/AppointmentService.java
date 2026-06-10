package com.example.hospital.service;

import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.Patient;

import java.util.List;

public interface AppointmentService {
	List<Appointment> getAppointmentsByPatient(Patient patient);

	List<Appointment> getAppointmentsByDoctor(Long doctorId);

	void updateAppointmentStatus(Long appointmentId, Appointment.Status status, String note);

	List<Appointment> findByDoctorIdOrderByAppointmentTime(Long doctorId);

}
