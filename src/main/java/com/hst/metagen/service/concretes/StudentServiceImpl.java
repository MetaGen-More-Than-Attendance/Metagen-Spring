package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Role;
import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.StudentRepository;
import com.hst.metagen.service.abstracts.FileService;
import com.hst.metagen.service.abstracts.RoleService;
import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.CreateStudentDto;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapperService modelMapperService;
    private final RoleService roleService;
    private final PasswordEncoder bcryptEncoder;
    private final StudentRepository studentRepository;
    private final FileService fileService;

    @Override
    public StudentDto save(CreateStudentDto studentDto) throws IOException {
        Role studentUser = roleService.getByRoleName("STUDENT_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(studentUser);
        Student student = modelMapperService.forRequest().map(studentDto, Student.class);

        if (studentDto.getImageBase64()!=null){
            student = fileService.saveFile(student,studentDto.getImageBase64());
        }

        student.setUserPassword(bcryptEncoder.encode(studentDto.getUserPassword()));
        student.setUserRoles(roles);

        return modelMapperService.forDto().map(studentRepository.save(student), StudentDto.class);
    }

    public StudentDto getStudent(Long studentId){
        return modelMapperService.forDto().map(studentRepository.getById(studentId), StudentDto.class);
    }

    @Override
    public byte[] getStudentPhoto(Long studentId) throws IOException {
        Student student = studentRepository.getById(studentId);
        Path path = Paths.get(student.getPhotoPath());
        return Files.readAllBytes(path);
    }
}
