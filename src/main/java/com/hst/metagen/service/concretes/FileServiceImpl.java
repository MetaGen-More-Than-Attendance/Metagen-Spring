package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Instructor;
import com.hst.metagen.entity.Student;
import com.hst.metagen.entity.User;
import com.hst.metagen.repository.UserRepository;
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

    private final UserRepository userRepository;

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
    public String saveFile(Student student,String base64Image) throws IOException {

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
        File file = new File(path);

        try (FileOutputStream fosFor = new FileOutputStream(file)) {
            fosFor.write(imageByte);
        }

        return path;
    }

    @Override
    public String saveFile(Instructor instructor, String base64Image) throws IOException {
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

        String path = getFileAbsolutePath(extension,"photos/",instructor.getIdentityNumber());
        File file = new File(path);

        try (FileOutputStream fosFor = new FileOutputStream(file)) {
            fosFor.write(imageByte);
        }

        return path;
    }

    @Override
    public byte[] getFile(Long id) throws IOException {
        User user = userRepository.getById(Math.toIntExact(id));
        Path path = Paths.get(user.getPhotoPath());
        return Files.readAllBytes(path);

    }
}
