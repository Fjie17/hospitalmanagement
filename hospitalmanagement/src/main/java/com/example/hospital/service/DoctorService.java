package com.example.hospital.service;

import java.util.List;
import java.util.Optional;

import com.example.hospital.dto.DoctorDTO;
import com.example.hospital.entity.Doctor;

public interface DoctorService {

	Doctor findByUsername(String username);

	List<Doctor> findAll();

	List<DoctorDTO> findAllWithDepartmentName();

	Optional<Doctor> findById(Long id);

	Doctor getDoctorById(Long id);

	List<Doctor> getAllDoctors();
}
