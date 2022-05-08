package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.LectureDto;
import com.hst.metagen.service.requests.lecture.CreateLectureRequest;

import java.util.List;

public interface LectureService {
    LectureDto save(CreateLectureRequest createLectureRequest);
    LectureDto getById(Long lectureId);
    List<LectureDto> getStudentLectures(Long studentId);
    List<LectureDto> getAllLectures();
    LectureDto update(Long lectureId,CreateLectureRequest createLectureRequest);
    Boolean delete(Long lectureId);
}
