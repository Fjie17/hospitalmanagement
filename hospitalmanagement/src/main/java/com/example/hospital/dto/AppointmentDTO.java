package com.example.hospital.dto;

import com.example.hospital.entity.Appointment;

import java.time.format.DateTimeFormatter;

public class AppointmentDTO {
	private Long id;
	private String patientName;
	private String appointmentTime;
	private String status;
	private String note;

	public AppointmentDTO(Appointment appointment) {
		this.id = appointment.getId();
		this.patientName = appointment.getPatient().getName();
		this.appointmentTime = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		this.status = appointment.getStatus().name();
		this.note = appointment.getNote();
	}

	public Long getId() {
		return id;
	}

	public String getPatientName() {
		return patientName;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public String getStatus() {
		return status;
	}

	public String getNote() {
		return note;
	}
}
