package com.example.hospital.controller;

import com.example.hospital.entity.Notice;
import com.example.hospital.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	/**
	 * 公告列表页（用于AJAX局部加载）
	 */
	@GetMapping("/list")
	public String noticeList(Model model) {
		try {
			List<Notice> notices = noticeService.getAllNotices();
			model.addAttribute("notices", notices);
			return "patient/notice-list"; // 确保模板文件存在
		} catch (Exception e) {
			e.printStackTrace();
			return "error/notice-error"; // 提供友好错误页面（可选）
		}
	}

	/**
	 * 公告详情页（用于AJAX局部加载）
	 */
	@GetMapping("/detail/{id}")
	public String noticeDetail(@PathVariable Long id, Model model) {
		try {
			Notice notice = noticeService.getNoticeById(id);
			if (notice != null) {
				model.addAttribute("notice", notice);
				return "patient/notice-detail";
			} else {
				model.addAttribute("error", "公告不存在");
				return "patient/notice-detail"; // 显示错误信息
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "加载公告时发生错误");
			return "patient/notice-detail"; // 显示错误信息
		}
	}
}
