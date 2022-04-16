package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.CreateStudentRequest;

import java.io.IOException;

public interface StudentService {
    StudentDto save(CreateStudentRequest createStudentRequest) throws IOException;
    StudentDto getStudent(Long studentId);
    byte[] getStudentPhoto(Long studentId) throws IOException;
}
