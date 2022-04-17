package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.LectureDto;
import com.hst.metagen.service.requests.CreateLectureRequest;

import java.util.List;

public interface LectureService {
    LectureDto save(CreateLectureRequest createLectureRequest);
    List<LectureDto> getStudentLectures(Long studentId);
    List<LectureDto> getAllLectures();
}
