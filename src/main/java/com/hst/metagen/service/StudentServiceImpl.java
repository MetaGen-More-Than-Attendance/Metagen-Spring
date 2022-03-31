package com.hst.metagen.service;

import com.hst.metagen.dto.StudentDto;
import com.hst.metagen.entity.Student;
import com.hst.metagen.mapper.StudentMapper;
import com.hst.metagen.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    //private final StudentMapper studentMapper;
    private final FileService fileService;

    private final StudentRepository studentRepository;
    @Override
    public Student save(StudentDto studentDto, MultipartFile file) throws IOException {
        Student student = StudentMapper.INSTANCE.studentToDto(studentDto);
        if (!file.isEmpty()){
            fileService.saveFile(student,file);
        }
        return studentRepository.save(student);
    }


    public Student getStudent(long id){
        return studentRepository.getById(id);
    }
}
