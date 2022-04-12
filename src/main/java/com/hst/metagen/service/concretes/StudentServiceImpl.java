package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Role;
import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.StudentRepository;
import com.hst.metagen.service.abstracts.RoleService;
import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.requests.CreateStudentDto;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapperService modelMapperService;
    private final RoleService roleService;
    private final PasswordEncoder bcryptEncoder;
    private final StudentRepository studentRepository;

    @Override
    public Student save(CreateStudentDto studentDto) {
        Role studentUser = roleService.getByRoleName("STUDENT_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(studentUser);
        Student student = modelMapperService.forRequest().map(studentDto, Student.class);

        student.setUserPassword(bcryptEncoder.encode(studentDto.getUserPassword()));
        student.setUserRoles(roles);

        return studentRepository.save(student);
    }

    public Student getStudent(Long studentId){
        return studentRepository.getById(studentId);
    }

    @Override
    public byte[] getStudentPhoto(Long studentId) {
        Student student = studentRepository.getById(studentId);
        return Base64.getDecoder().decode(student.getImageBase64());
    }
}
