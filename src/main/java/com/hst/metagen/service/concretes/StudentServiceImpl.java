package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.StudentRepository;
import com.hst.metagen.service.abstracts.FileService;
import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.requests.CreateStudentDto;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapperService modelMapperService;
    private final FileService fileService;

    private final StudentRepository studentRepository;

    @Override
    public Student save(CreateStudentDto studentDto, MultipartFile file) throws IOException {
        Student student = modelMapperService.forRequest().map(studentDto, Student.class); //StudentMapper.INSTANCE.studentToDto(studentDto);
        if (!file.isEmpty()){
            student = fileService.saveFile(student,file);
        }
        return studentRepository.save(student);
    }


    public Student getStudent(Long id){
        return studentRepository.getById(id);
    }

    @Override
    public byte[] getStudentPhoto(Long id) throws IOException {
        Student student = studentRepository.getById(id);
        Path path = Paths.get(student.getPhotoPath());
        byte[] data = Files.readAllBytes(path);
        return data;
    }
}
