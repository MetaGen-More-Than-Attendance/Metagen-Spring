package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.AbsenteeismDto;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import com.hst.metagen.service.requests.absenteeism.UpdateAbsenteeismRequest;

import java.time.LocalDate;
import java.util.List;

public interface AbsenteeismService {
    void save(CreateAbsenteeismRequest createAbsenteeismRequest);
    AbsenteeismDto update(UpdateAbsenteeismRequest updateAbsenteeismRequest);
    List<AbsenteeismDto> getStudentAndLectureAbsenteeisms(Long studentId, Long lectureId);
    List<AbsenteeismDto> getLectureAbsenteesimsOnDate(Long lectureId, LocalDate localDate);
}
