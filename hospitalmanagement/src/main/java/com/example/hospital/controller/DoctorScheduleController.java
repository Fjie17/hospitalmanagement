package com.example.hospital.controller;

import com.example.hospital.dto.ScheduleDTO;
import com.example.hospital.entity.Schedule;
import com.example.hospital.security.CustomUserDetails;
import com.example.hospital.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DoctorScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/doctor/schedule")
    @ResponseBody
    public List<ScheduleDTO> viewSchedule(Authentication authentication) {
        Long doctorId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        List<Schedule> schedules = scheduleService.getFutureSchedulesByDoctorId(doctorId);
        return schedules.stream().map(ScheduleDTO::new).toList();
    }

    @GetMapping("/doctor/schedule/page")
    public String showSchedulePage() {
        return "doctor/schedule";
    }



}
