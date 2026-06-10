package com.example.hospital.controller;

import com.example.hospital.entity.HealthTip;
import com.example.hospital.repository.HealthTipRepository;
import com.example.hospital.service.HealthTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-tips")
public class HealthTipController {
	
	private final HealthTipRepository healthTipRepository;

    @Autowired
    public HealthTipController(HealthTipRepository healthTipRepository) {
        this.healthTipRepository = healthTipRepository;
    }
    
    @Autowired
    private HealthTipService healthTipService;

    @GetMapping("/search")
    public List<HealthTip> searchTips(@RequestParam(required = false) String keyword) {
        return healthTipService.searchHealthTips(keyword);
    }
    
    @GetMapping("/recent")
    public List<HealthTip> getRecentTips(@RequestParam(defaultValue = "4") int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createTime"));
        return healthTipRepository.findAll(pageable).getContent();
    }

}
