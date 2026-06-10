package com.example.hospital.service.impl;

import com.example.hospital.entity.MedicalRecord;
import com.example.hospital.entity.Patient;
import com.example.hospital.repository.MedicalRecordRepository;
import com.example.hospital.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

	@Autowired
	private MedicalRecordRepository recordRepository;

	@Override
	public List<MedicalRecord> findByPatient(Patient patient) {
		return recordRepository.findByPatient(patient);
	}

	@Override
	public List<MedicalRecord> findByDoctor(Long doctorId) {
		return recordRepository.findByDoctorId(doctorId);
	}

	@Override
	public MedicalRecord findByPatientAndDoctor(Long patientId, Long doctorId) {
		return recordRepository.findByPatientIdAndDoctorId(patientId, doctorId).orElse(null);
	}

	@Override
	public MedicalRecord save(MedicalRecord record) {
		return recordRepository.save(record);
	}

}
