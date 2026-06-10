package com.example.hospital.service.impl;

import com.example.hospital.entity.ContactMessage;
import com.example.hospital.repository.ContactMessageRepository;
import com.example.hospital.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

	@Autowired
	private ContactMessageRepository contactMessageRepository;

	@Override
	public void saveMessage(ContactMessage message) {
		contactMessageRepository.save(message);
	}
}
