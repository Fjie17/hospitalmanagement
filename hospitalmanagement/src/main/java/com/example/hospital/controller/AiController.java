package com.example.hospital.controller;

import com.example.hospital.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

	@Autowired
	private AiService aiService;

	@PostMapping(value = "/ask", consumes = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String ask(@RequestBody String question) {
		return aiService.askAi(question);
	}

}
