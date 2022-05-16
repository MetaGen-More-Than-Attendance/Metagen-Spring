package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Semester;
import com.hst.metagen.service.dtos.SemesterDto;
import com.hst.metagen.service.requests.semester.CreateSemesterRequest;

import java.util.List;

public interface SemesterService {
    SemesterDto save(CreateSemesterRequest createSemesterRequest);
    List<SemesterDto> getAllSemesters();
    Semester getLastSemester();
}
