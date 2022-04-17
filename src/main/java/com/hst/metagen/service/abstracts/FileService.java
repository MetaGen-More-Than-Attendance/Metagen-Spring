package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Instructor;
import com.hst.metagen.entity.Student;

import java.io.IOException;

public interface FileService {
    String getFileAbsolutePath(String extension, String fileBasePath, String identityNumber) throws IOException;

    String saveFile(Student student,String base64Image) throws IOException;
    String saveFile(Instructor instructor, String base64Image) throws IOException;

    byte[] getFile(Long id) throws IOException;
}
