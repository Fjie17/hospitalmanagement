package com.example.hospital.repository;

import com.example.hospital.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findByDoctorIdAndWorkDateAfterOrderByWorkDateAsc(Long doctorId, LocalDate date);

	// ⬇️ 新增用于查找特定排班记录
	Optional<Schedule> findByDoctorIdAndWorkDateAndTimeSlot(Long doctorId, LocalDate workDate,
			Schedule.TimeSlot timeSlot);
}
