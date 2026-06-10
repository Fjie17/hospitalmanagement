package com.example.hospital.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {

	private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";

	@Value("${openrouter.api.key:}")
	private String apiKey;

	public String askAi(String question) {
		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("model", "deepseek/deepseek-chat-v3-0324:free");
		requestBody.put("messages", List.of(Map.of("role", "user", "content", question)));
		requestBody.put("temperature", 0.7);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.exchange(OPENROUTER_API_URL, HttpMethod.POST, entity, Map.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> body = response.getBody();
			if (body != null && body.containsKey("choices")) {
				List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
				Map<String, Object> firstChoice = choices.get(0);
				Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
				return message.get("content").toString();
			}
		}

		return "AI 暂时无法响应，请稍后再试。";
	}
}
