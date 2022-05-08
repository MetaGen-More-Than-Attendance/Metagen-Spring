package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Role;
import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.StudentRepository;
import com.hst.metagen.service.abstracts.FileService;
import com.hst.metagen.service.abstracts.RoleService;
import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.student.CreateStudentRequest;
import com.hst.metagen.util.exception.NotFoundException;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
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
    public StudentDto save(CreateStudentRequest createStudentRequest) throws IOException {
        Role studentUser = roleService.getByRoleName("STUDENT_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(studentUser);
        Student student = modelMapperService.forRequest().map(createStudentRequest, Student.class);
        String photoPath = "";

        if (createStudentRequest.getImageBase64()!=null){
            photoPath = fileService.saveFile(student,createStudentRequest.getImageBase64());
        }

        student.setUserPassword(bcryptEncoder.encode(createStudentRequest.getUserPassword()));
        student.setUserRoles(roles);
        student.setPhotoPath(photoPath);

        student = studentRepository.save(student);

        return modelMapperService.entityToDto(student, StudentDto.class);
    }

    public StudentDto getStudent(Long studentId){
        return modelMapperService.entityToDto(studentRepository.getById(studentId), StudentDto.class);
    }

    @Override
    public byte[] getStudentPhoto(Long studentId) throws IOException {
        Student student = studentRepository.getById(studentId);
        Path path = Paths.get(student.getPhotoPath());
        return Files.readAllBytes(path);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return modelMapperService.entityToDtoList(studentList, StudentDto.class);
    }

    @Override
    public Boolean deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(NotFoundException::new);
        studentRepository.delete(student);
        return true;
    }
}
