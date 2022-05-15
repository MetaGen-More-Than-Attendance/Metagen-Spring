package com.hst.metagen.service.concretes;

import com.hst.metagen.service.abstracts.AbsenteeismService;
import com.hst.metagen.service.dtos.AbsenteeismDto;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AbsenteeismServiceImpl implements AbsenteeismService {
    @Override
    public AbsenteeismDto save(CreateAbsenteeismRequest createAbsenteeismRequest) {
        return null;
    }

    @Override
    public List<AbsenteeismDto> getStudentAndLectureAbsenteeisms(Long studentId, Long lectureId) {
        return null;
    }

    @Override
    public List<AbsenteeismDto> getLectureAbsenteesimsOnDate(Long lectureId, LocalDate localDate) {
        return null;
    }
}
