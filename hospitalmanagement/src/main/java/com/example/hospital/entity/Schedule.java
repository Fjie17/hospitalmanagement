package com.example.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "schedule")
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	private LocalDate workDate;

	@Enumerated(EnumType.STRING)
	private TimeSlot timeSlot;

	private Integer maxPatients;

	private Integer currentPatients;

	public enum TimeSlot {
		上午, 下午, 晚上
	}

	// ⬇️ Getter / Setter （手写或生成）
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDate getWorkDate() {
		return workDate;
	}

	public void setWorkDate(LocalDate workDate) {
		this.workDate = workDate;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Integer getMaxPatients() {
		return maxPatients;
	}

	public void setMaxPatients(Integer maxPatients) {
		this.maxPatients = maxPatients;
	}

	public Integer getCurrentPatients() {
		return currentPatients;
	}

	public void setCurrentPatients(Integer currentPatients) {
		this.currentPatients = currentPatients;
	}
}
