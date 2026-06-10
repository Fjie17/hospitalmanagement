package com.example.hospital.dto;

import com.example.hospital.entity.Schedule;

public class ScheduleDTO {
	private String workDate;
	private String timeSlot;
	private int maxPatients;
	private int currentPatients;

	// 构造函数
	public ScheduleDTO(Schedule s) {
		this.workDate = s.getWorkDate().toString();
		this.timeSlot = s.getTimeSlot().name();
		this.maxPatients = s.getMaxPatients();
		this.currentPatients = s.getCurrentPatients();
	}

	// Getter
	public String getWorkDate() {
		return workDate;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public int getMaxPatients() {
		return maxPatients;
	}

	public int getCurrentPatients() {
		return currentPatients;
	}
}
