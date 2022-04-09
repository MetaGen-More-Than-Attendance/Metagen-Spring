package com.hst.metagen.service.abstracts;

import com.hst.metagen.dto.StudentDto;
import com.hst.metagen.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {
    Student save(StudentDto studentDto, MultipartFile file) throws IOException;
    Student getStudent(long id);
    Byte[] getStudentPhoto(long id);
}
