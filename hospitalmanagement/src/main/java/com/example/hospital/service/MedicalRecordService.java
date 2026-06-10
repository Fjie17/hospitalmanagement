package com.example.hospital.service;

import com.example.hospital.entity.MedicalRecord;
import com.example.hospital.entity.Patient;

import java.util.List;

public interface MedicalRecordService {
	List<MedicalRecord> findByPatient(Patient patient);

	List<MedicalRecord> findByDoctor(Long doctorId);

	MedicalRecord findByPatientAndDoctor(Long patientId, Long doctorId);

	MedicalRecord save(MedicalRecord record);

}
