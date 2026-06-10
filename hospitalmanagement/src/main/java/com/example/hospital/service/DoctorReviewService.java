package com.example.hospital.service;

import com.example.hospital.entity.DoctorReview;
import com.example.hospital.entity.Doctor;

import java.util.List;

public interface DoctorReviewService {
    void addReview(DoctorReview review);
    List<DoctorReview> getReviewsByDoctor(Doctor doctor);
}
