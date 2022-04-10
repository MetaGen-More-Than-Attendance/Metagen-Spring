package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.StudentRepository;
import com.hst.metagen.service.abstracts.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final StudentRepository studentRepository;
    @Override
    public String getFileAbsolutePath(MultipartFile multipartFile, String fileBasePath,String identityNumber) throws IOException {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String filePath =  fileBasePath + identityNumber + "." + extension;
        Path basePath = Paths.get(fileBasePath);
        if (!Files.exists(basePath)){
            Files.createDirectories(basePath);
        }
        return filePath;
    }

    @Override
    public Student saveFile(Student student,MultipartFile multipartFile) throws IOException {
        String path = getFileAbsolutePath(multipartFile,"photos/",student.getIdentityNumber());
        student.setPhotoPath(path);
        byte[] image = multipartFile.getBytes();
        File file = new File(path);

        try (FileOutputStream fosFor = new FileOutputStream(file)) {
            fosFor.write(image);
        }
        return student;
    }

    @Override
    public byte[] getFile(Long studentId) throws IOException {
        Student student = studentRepository.getById(studentId);
        Path path = Paths.get(student.getPhotoPath());
        byte[] data = Files.readAllBytes(path);
        return data;

    }
}
