package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.*;
import com.hst.metagen.repository.DepartmentRepository;
import com.hst.metagen.repository.LectureRepository;
import com.hst.metagen.repository.StudentRepository;
import com.hst.metagen.service.abstracts.FileService;
import com.hst.metagen.service.abstracts.LectureService;
import com.hst.metagen.service.abstracts.RoleService;
import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.dtos.InstructorDto;
import com.hst.metagen.service.dtos.LectureDto;
import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.student.CreateStudentRequest;
import com.hst.metagen.service.requests.student.UpdateStudentRequest;
import com.hst.metagen.util.exception.NotFoundException;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapperService modelMapperService;
    private final RoleService roleService;
    private final PasswordEncoder bcryptEncoder;
    private final StudentRepository studentRepository;
    private final FileService fileService;
    private final LectureRepository lectureRepository;
    private final DepartmentRepository departmentRepository;
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
        if (createStudentRequest.getDepartmentId() != null){
            Department department = departmentRepository.getById(createStudentRequest.getDepartmentId());
            student.setDepartment(department);
        }

        student.setUserPassword(bcryptEncoder.encode(createStudentRequest.getUserPassword()));
        student.setUserRoles(roles);
        student.setPhotoPath(photoPath);
        student = studentRepository.save(student);
        student.setStudentId(student.getUserId());
        return modelMapperService.entityToDto(student, StudentDto.class);
    }

    public StudentDto getStudent(Long studentId){
        return modelMapperService.entityToDto(studentRepository.findById(studentId).orElseThrow(NotFoundException::new), StudentDto.class);
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

    @Override
    public StudentDto update(Long studentId,UpdateStudentRequest updateStudentRequest) throws IOException {
        Student student = studentRepository.findById(studentId).orElseThrow(NotFoundException::new);
        if (updateStudentRequest.getUserName() != null)
            student.setUserName(updateStudentRequest.getUserName());
        if (updateStudentRequest.getUserSurname() != null)
            student.setUserSurname(updateStudentRequest.getUserSurname());
        if (updateStudentRequest.getIdentityNumber() != null)
            student.setIdentityNumber(updateStudentRequest.getIdentityNumber());
        if (updateStudentRequest.getUserMail() != null)
            student.setUserMail(updateStudentRequest.getUserMail());
        String photoPath = "";
        if (updateStudentRequest.getImageBase64() != null){
            photoPath = fileService.saveFile(student,updateStudentRequest.getImageBase64());
            student.setPhotoPath(photoPath);
        }
        if (updateStudentRequest.getDepartmentId() != null){
            Department department = departmentRepository.getById(updateStudentRequest.getDepartmentId());
            student.setDepartment(department);
        }
        Student savedStudent = studentRepository.save(student);
        return modelMapperService.entityToDto(savedStudent, StudentDto.class);
    }

    @Override
    public List<LectureDto> getStudentLectures(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(NotFoundException::new);
        List<Lecture> lecture =  lectureRepository.findLecturesByLectureStudents(student);
        return modelMapperService.entityToDtoList(lecture,LectureDto.class);
    }

    @Override
    public List<StudentDto> getAllByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(NotFoundException::new);
        List<StudentDto> studentDtos = modelMapperService.entityToDtoList(studentRepository.getStudentsByDepartment(department),StudentDto.class);
        return studentDtos;
    }
}
