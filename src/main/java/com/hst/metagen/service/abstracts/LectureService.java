package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Lecture;
import com.hst.metagen.service.dtos.LectureDto;
import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.lecture.CreateLectureRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface LectureService {
    LectureDto save(CreateLectureRequest createLectureRequest);
    LectureDto getById(Long lectureId);
    List<LectureDto> getStudentLectures(Long studentId);
    List<LectureDto> getAllLectures();
    LectureDto update(Long lectureId,CreateLectureRequest createLectureRequest);
    Boolean delete(Long lectureId);
    LectureDto addStudent(Long lectureId, List<Long> studentIds) throws MessagingException, UnsupportedEncodingException;
    Lecture getLectureEntity(Long lectureId);
    List<LectureDto> getInstructorLectures(Long instructorId);
    List<StudentDto> getLectureStudents(Long lectureId);
}
