package com.example.hospital.service.impl;

import com.example.hospital.entity.HealthTip;
import com.example.hospital.repository.HealthTipRepository;
import com.example.hospital.service.HealthTipService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthTipServiceImpl implements HealthTipService {

	private final HealthTipRepository healthTipRepository;

	public HealthTipServiceImpl(HealthTipRepository healthTipRepository) {
		this.healthTipRepository = healthTipRepository;
	}

	@Override
	public List<HealthTip> searchHealthTips(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return healthTipRepository.findAll();
		}
		return healthTipRepository.searchByKeyword(keyword);
	}

	@Override
	public List<HealthTip> findAll() {
		return healthTipRepository.findAll();
	}

	@Override
	public HealthTip findById(Long id) {
		return healthTipRepository.findById(id).orElse(null);
	}

	@Override
	public HealthTip save(HealthTip healthTip) {
		return healthTipRepository.save(healthTip);
	}

	@Override
	public void deleteById(Long id) {
		healthTipRepository.deleteById(id);
	}

	@Override
	public List<HealthTip> findAllTips() {
		return healthTipRepository.findAll();
	}
}
