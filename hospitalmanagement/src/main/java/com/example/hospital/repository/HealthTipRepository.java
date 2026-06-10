package com.example.hospital.repository;

import com.example.hospital.entity.HealthTip;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthTipRepository extends JpaRepository<HealthTip, Long> {

	@Query("SELECT h FROM HealthTip h WHERE LOWER(h.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(h.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<HealthTip> searchByKeyword(@Param("keyword") String keyword);

}
