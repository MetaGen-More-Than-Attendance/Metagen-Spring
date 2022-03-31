package com.hst.metagen.service;

import com.hst.metagen.entity.Student;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String getFileAbsolutePath(MultipartFile multipartFile, String fileBasePath,String identityNumber) throws IOException {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String filePath =  fileBasePath +"id#"+identityNumber+"#date#"+ new Date().getTime() + "."+extension;
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
}
