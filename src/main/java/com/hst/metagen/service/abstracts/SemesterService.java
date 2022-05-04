package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.SemesterDto;
import com.hst.metagen.service.requests.CreateSemesterRequest;

import java.util.List;

public interface SemesterService {
    SemesterDto save(CreateSemesterRequest createSemesterRequest);
    List<SemesterDto> getAllSemesters();
}