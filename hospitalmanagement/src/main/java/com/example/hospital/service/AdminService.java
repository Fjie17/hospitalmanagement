package com.example.hospital.service;

import com.example.hospital.entity.Admin;

public interface AdminService {
	Admin findByUsername(String username);
}
