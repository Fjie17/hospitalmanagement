package com.example.hospital.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.Data;

import com.example.hospital.entity.Admin;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.repository.AdminRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private DoctorRepository doctorRepo;
	@Autowired
	private PatientRepository patientRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Admin> admin = adminRepo.findByUsername(username);
		if (admin.isPresent()) {
			Admin a = admin.get();
			return new CustomUserDetails(a.getUsername(), a.getPassword(), "ADMIN", a.getId());
		}

		Optional<Doctor> doctor = doctorRepo.findByUsername(username);
		if (doctor.isPresent()) {
			Doctor d = doctor.get();
			return new CustomUserDetails(d.getUsername(), d.getPassword(), "DOCTOR", d.getId());
		}

		Optional<Patient> patient = patientRepo.findByUsername(username);
		if (patient.isPresent()) {
			Patient p = patient.get();
			return new CustomUserDetails(p.getUsername(), p.getPassword(), "PATIENT", p.getId());
		}

		throw new UsernameNotFoundException("User not found");
	}
}
