package com.example.hospital.repository;

import com.example.hospital.entity.DoctorReview;
import com.example.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorReviewRepository extends JpaRepository<DoctorReview, Long> {
	List<DoctorReview> findByDoctorOrderByReviewTimeDesc(Doctor doctor);
}
