package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.AbsenteeismDto;
import com.hst.metagen.service.dtos.AbsenteeismResponse;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import com.hst.metagen.service.requests.absenteeism.UpdateAbsenteeismRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AbsenteeismService {
    void save(CreateAbsenteeismRequest createAbsenteeismRequest);
    AbsenteeismDto update(UpdateAbsenteeismRequest updateAbsenteeismRequest);
    Map<Object, Object> getStudentAndLectureAbsenteeisms(Long studentId, Long lectureId, Long semesterId);
    AbsenteeismResponse getLectureAbsenteesimsOnDate(Long lectureId, LocalDate localDate);
    AbsenteeismResponse getLectureAbsenteesims(Long lectureId, Long semesterId);
    void deleteAll();
}
