package com.example.hospital.service.impl;

import com.example.hospital.dto.DoctorDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.service.DoctorService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public Doctor findByUsername(String username) {
		return doctorRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("找不到用户名为 " + username + " 的医生"));
	}

	@Override
	public List<Doctor> findAll() {
		return doctorRepository.findAll();
	}

	public DoctorServiceImpl(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}

	@Override
	public List<DoctorDTO> findAllWithDepartmentName() {
		return doctorRepository.findAllWithDepartmentName();
	}

	@Override
	public Optional<Doctor> findById(Long id) {
		return doctorRepository.findById(id);
	}

	@Override
	public Doctor getDoctorById(Long id) {
		return doctorRepository.findById(id).orElse(null);
	}

	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

}
