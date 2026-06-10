package com.example.hospital.service.impl;

import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public boolean registerDoctor(Doctor doctor) {
		Optional<Doctor> existingDoctor = doctorRepository.findByUsername(doctor.getUsername());
		if (existingDoctor.isPresent()) {
			return false; // 用户名已存在
		}
		// 密码加密
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
		doctorRepository.save(doctor);
		return true;
	}

	@Override
	public boolean registerPatient(Patient patient) {
		Optional<Patient> existingPatient = patientRepository.findByUsername(patient.getUsername());
		if (existingPatient.isPresent()) {
			return false; // 用户名已存在
		}
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		patientRepository.save(patient);
		return true;
	}
}
