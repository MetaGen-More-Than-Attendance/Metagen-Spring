package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.StudentRepository;
import com.hst.metagen.service.abstracts.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final StudentRepository studentRepository;
    @Override
    public String getFileAbsolutePath(String extension, String fileBasePath,String identityNumber) throws IOException {

        String filePath =  fileBasePath + identityNumber + "." + extension;
        Path basePath = Paths.get(fileBasePath);
        if (!Files.exists(basePath)){
            Files.createDirectories(basePath);
        }
        return filePath;
    }

    @Override
    public Student saveFile(Student student,String base64Image) throws IOException {

        String[] strings = base64Image.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }

        byte[] imageByte = Base64.getDecoder().decode(strings[1]);

        String path = getFileAbsolutePath(extension,"photos/",student.getIdentityNumber());
        student.setPhotoPath(path);
        File file = new File(path);

        try (FileOutputStream fosFor = new FileOutputStream(file)) {
            fosFor.write(imageByte);
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
