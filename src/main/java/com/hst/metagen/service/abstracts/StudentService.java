package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Student;
import com.hst.metagen.service.requests.CreateStudentDto;

public interface StudentService {
    Student save(CreateStudentDto createStudentDto);
    Student getStudent(Long studentId);
    byte[] getStudentPhoto(Long studentId);
}
