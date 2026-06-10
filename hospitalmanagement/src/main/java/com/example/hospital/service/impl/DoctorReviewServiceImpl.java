package com.example.hospital.service.impl;

import com.example.hospital.entity.DoctorReview;
import com.example.hospital.entity.Doctor;
import com.example.hospital.repository.DoctorReviewRepository;
import com.example.hospital.service.DoctorReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorReviewServiceImpl implements DoctorReviewService {

	@Autowired
	private DoctorReviewRepository doctorReviewRepository;

	@Override
	public void addReview(DoctorReview review) {
		doctorReviewRepository.save(review);
	}

	@Override
	public List<DoctorReview> getReviewsByDoctor(Doctor doctor) {
		return doctorReviewRepository.findByDoctorOrderByReviewTimeDesc(doctor);
	}
}
