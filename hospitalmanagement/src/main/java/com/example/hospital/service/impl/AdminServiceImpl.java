package com.example.hospital.service.impl;

import com.example.hospital.entity.Admin;
import com.example.hospital.repository.AdminRepository;
import com.example.hospital.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin findByUsername(String username) {
		return adminRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("找不到用户名为 " + username + " 的管理员"));
	}
}
