package com.example.hospital.service.impl;

import com.example.hospital.entity.Patient;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Patient findByUsername(String username) {
		return patientRepository.findByUsername(username).orElse(null);
	}

	@Override
	public void updatePhoneAndBirthDate(String username, String phone, LocalDate birthDate) {
		Patient patient = patientRepository.findByUsername(username).orElse(null);
		if (patient != null) {
			patient.setPhone(phone);
			patient.setBirthDate(birthDate);
			patientRepository.save(patient);
		}
	}
}
