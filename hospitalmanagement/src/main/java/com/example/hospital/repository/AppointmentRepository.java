package com.example.hospital.repository;

import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findByDepartment_Id(Long departmentId); // 正确写法

	List<Appointment> findByPatient(Patient patient);

	// 增加按 doctor 查找的接口
	List<Appointment> findByDoctorOrderByAppointmentTimeAsc(Doctor doctor);

	List<Appointment> findByDoctorIdOrderByAppointmentTimeAsc(Long doctorId);

	@Query("SELECT a FROM Appointment a JOIN FETCH a.patient WHERE a.doctor.id = :doctorId ORDER BY a.appointmentTime")
	List<Appointment> findByDoctorIdWithPatient(@Param("doctorId") Long doctorId);

}
