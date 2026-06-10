package com.example.hospital.service.impl;

import com.example.hospital.entity.Notice;
import com.example.hospital.repository.NoticeRepository;
import com.example.hospital.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;

	@Override
	public List<Notice> getAllNotices() {
		return noticeRepository.findAllByOrderByCreatedTimeDesc(); // 确保调用的是NoticeRepository
	}

	@Override
	public Notice getNoticeById(Long id) {
		return noticeRepository.findById(id).orElse(null);
	}
}
