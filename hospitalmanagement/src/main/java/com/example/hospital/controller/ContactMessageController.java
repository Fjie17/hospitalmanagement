package com.example.hospital.controller;

import com.example.hospital.entity.ContactMessage;
import com.example.hospital.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactMessageController {

	@Autowired
	private ContactMessageService contactMessageService;

	@PostMapping("/submit")
	public String submitMessage(@ModelAttribute ContactMessage message) {
		contactMessageService.saveMessage(message);
		return "success";
	}
}
