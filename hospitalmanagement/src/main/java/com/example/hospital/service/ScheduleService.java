package com.example.hospital.service;

import com.example.hospital.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> getFutureSchedulesByDoctorId(Long doctorId);
    
    List<Schedule> findFutureSchedulesByDoctorId(Long doctorId);
}
