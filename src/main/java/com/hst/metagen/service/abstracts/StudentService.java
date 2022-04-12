package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Student;
import com.hst.metagen.service.requests.CreateStudentDto;

import java.io.IOException;

public interface StudentService {
    Student save(CreateStudentDto createStudentDto, String base64Image) throws IOException;
    Student getStudent(Long id);
    byte[] getStudentPhoto(Long id) throws IOException;
}
