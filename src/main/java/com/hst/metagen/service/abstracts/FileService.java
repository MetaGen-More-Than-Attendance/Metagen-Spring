package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Student;

import java.io.IOException;

public interface FileService {
    String getFileAbsolutePath(String extension, String fileBasePath, String identityNumber) throws IOException;

    Student saveFile(Student student,String base64Image) throws IOException;

    byte[] getFile(Long studentId) throws IOException;
}
