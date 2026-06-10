package com.example.hospital.repository;

import com.example.hospital.dto.DoctorDTO;
import com.example.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
    List<Doctor> findByDepartmentId(Long departmentId);
    
    Optional<Doctor> findByUsername(String username);
    
    @Query("SELECT new com.example.hospital.dto.DoctorDTO(d.name, d.phone, d.title, dept.name, d.gender) " +
            "FROM Doctor d JOIN Department dept ON d.departmentId = dept.id")
     List<DoctorDTO> findAllWithDepartmentName();
}
