package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Student;
import com.hst.metagen.service.requests.CreateStudentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {
    Student save(CreateStudentDto createStudentDto, MultipartFile file) throws IOException;
    Student getStudent(Long id);
    byte[] getStudentPhoto(Long id) throws IOException;
}
