package com.hst.metagen.service;

import com.hst.metagen.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String getFileAbsolutePath(MultipartFile multipartFile, String fileBasePath, String identityNumber) throws IOException;

    Student saveFile(Student student,MultipartFile multipartFile) throws IOException;
}
