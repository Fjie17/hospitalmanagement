package com.example.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@Column(name = "appointment_time", nullable = false)
	private LocalDateTime appointmentTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.待确认;

	@Column(columnDefinition = "TEXT")
	private String note;

	public enum Status {
		待确认, 已确认, 已取消
	}

	// ---------- Constructors ----------
	public Appointment() {
	}

	public Appointment(Patient patient, Doctor doctor, LocalDateTime appointmentTime, Status status, String note) {
		this.patient = patient;
		this.doctor = doctor;
		this.appointmentTime = appointmentTime;
		this.status = status;
		this.note = note;
	}

	// ---------- Getters and Setters ----------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
