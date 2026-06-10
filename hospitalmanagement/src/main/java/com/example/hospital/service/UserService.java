package com.example.hospital.service;

import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;

public interface UserService {
	boolean registerDoctor(Doctor doctor);

	boolean registerPatient(Patient patient);
}
