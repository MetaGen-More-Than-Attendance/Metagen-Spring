package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.CreateStudentDto;

import java.io.IOException;

public interface StudentService {
    StudentDto save(CreateStudentDto createStudentDto) throws IOException;
    StudentDto getStudent(Long studentId);
    byte[] getStudentPhoto(Long studentId) throws IOException;
}
