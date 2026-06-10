package com.example.hospital.service;

import com.example.hospital.entity.Notice;
import java.util.List;

public interface NoticeService {
	List<Notice> getAllNotices();

	Notice getNoticeById(Long id);
}
