package com.example.hospital.service;

import com.example.hospital.entity.Patient;
import java.time.LocalDate;

public interface PatientService {

	Patient findByUsername(String username);

	void updatePhoneAndBirthDate(String username, String phone, LocalDate birthDate);

}
