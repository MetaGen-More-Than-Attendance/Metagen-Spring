package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.LectureDto;
import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.student.CreateStudentRequest;
import com.hst.metagen.service.requests.student.UpdateStudentRequest;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    StudentDto save(CreateStudentRequest createStudentRequest) throws IOException;
    StudentDto getStudent(Long studentId);
    byte[] getStudentPhoto(Long studentId) throws IOException;
    List<StudentDto> getAllStudents();
    Boolean deleteStudent(Long studentId);
    StudentDto update(Long studentId,UpdateStudentRequest updateStudentRequest) throws IOException;
    List<LectureDto> getStudentLectures(Long studentId);
    List<StudentDto> getAllByDepartment(Long departmentId);
    String getStudentPhotoBase64(Long studentId) throws IOException;
}
