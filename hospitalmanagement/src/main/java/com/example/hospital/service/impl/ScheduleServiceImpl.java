package com.example.hospital.service.impl;

import com.example.hospital.entity.Schedule;
import com.example.hospital.repository.ScheduleRepository;
import com.example.hospital.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> getFutureSchedulesByDoctorId(Long doctorId) {
        return scheduleRepository.findByDoctorIdAndWorkDateAfterOrderByWorkDateAsc(doctorId, LocalDate.now().minusDays(1));
    }
    
    @Override
    public List<Schedule> findFutureSchedulesByDoctorId(Long doctorId) {
        return scheduleRepository.findByDoctorIdAndWorkDateAfterOrderByWorkDateAsc(doctorId, LocalDate.now());
    }
}
