package com.example.hospital.service;

import com.example.hospital.entity.HealthTip;

import java.util.List;

public interface HealthTipService {

	List<HealthTip> findAllTips();

	List<HealthTip> findAll();

	HealthTip findById(Long id);

	HealthTip save(HealthTip healthTip);

	void deleteById(Long id);

	List<HealthTip> searchHealthTips(String keyword);
}
