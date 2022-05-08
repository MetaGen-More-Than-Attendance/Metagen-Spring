package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.AbsenteeismDto;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;

import java.time.LocalDate;
import java.util.List;

public interface AbsenteeismService {
    AbsenteeismDto save(CreateAbsenteeismRequest createAbsenteeismRequest);
    List<AbsenteeismDto> getStudentAndLectureAbsenteeisms(Long studentId, Long lectureId);
    List<AbsenteeismDto> getLectureAbsenteesimsOnDate(Long lectureId, LocalDate localDate);
}
