package com.example.hospital.repository;

import com.example.hospital.entity.MedicalRecord;
import com.example.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
	List<MedicalRecord> findByPatient(Patient patient);

	List<MedicalRecord> findByDoctorId(Long doctorId);

	Optional<MedicalRecord> findByPatientIdAndDoctorId(Long patientId, Long doctorId);

}
