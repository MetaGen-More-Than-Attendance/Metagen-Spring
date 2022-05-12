package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Instructor;
import com.hst.metagen.entity.Lecture;
import com.hst.metagen.entity.Role;
import com.hst.metagen.repository.InstructorRepository;
import com.hst.metagen.repository.LectureRepository;
import com.hst.metagen.service.abstracts.FileService;
import com.hst.metagen.service.abstracts.InstructorService;
import com.hst.metagen.service.abstracts.RoleService;
import com.hst.metagen.service.dtos.InstructorDto;
import com.hst.metagen.service.requests.instructor.CreateInstructorRequest;
import com.hst.metagen.service.requests.instructor.UpdateInstructorRequest;
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

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final ModelMapperService modelMapperService;
    private final RoleService roleService;
    private final PasswordEncoder bcryptEncoder;
    private final InstructorRepository instructorRepository;
    private final FileService fileService;
    private final LectureRepository lectureRepository;
    @Override
    public InstructorDto save(CreateInstructorRequest createInstructorRequest) throws IOException {
        Role instructorUser = roleService.getByRoleName("TEACHER_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(instructorUser);
        Instructor instructor = modelMapperService.forRequest().map(createInstructorRequest, Instructor.class);
        String photoPath = "";

        if (createInstructorRequest.getImageBase64()!=null){
            photoPath = fileService.saveFile(instructor,createInstructorRequest.getImageBase64());
        }

        instructor.setUserPassword(bcryptEncoder.encode(createInstructorRequest.getUserPassword()));
        instructor.setUserRoles(roles);
        instructor.setPhotoPath(photoPath);

        return modelMapperService.forDto().map(instructorRepository.save(instructor), InstructorDto.class);
    }

    @Override
    public InstructorDto getInstructor(Long instructorId) {
        return modelMapperService.forDto().map(instructorRepository.getById(instructorId), InstructorDto.class);
    }

    @Override
    public byte[] getInstructorPhoto(Long instructorId) throws IOException {
        Instructor instructor = instructorRepository.getById(instructorId);
        Path path = Paths.get(instructor.getPhotoPath());
        return Files.readAllBytes(path);
    }

    @Override
    public List<InstructorDto> getAllInstructor() {
        List<Instructor> instructorList = instructorRepository.findAll();
        return modelMapperService.entityToDtoList(instructorList, InstructorDto.class);
    }

    @Override
    public Boolean deleteInstructor(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(NotFoundException::new);
        instructor.setLectures(null);
        List<Lecture> lectures = lectureRepository.findLecturesByInstructor(instructor);
        if (lectures.isEmpty()){
            instructorRepository.delete(instructor);
            return true;
        }
        for (Lecture lecture : lectures){
            lecture.setInstructor(null);
            //lectureRepository.save(lecture);
        }
        instructorRepository.delete(instructor);
        return true;
    }

    @Override
    public InstructorDto update(Long instructorId, UpdateInstructorRequest updateInstructorRequest) throws IOException {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(NotFoundException::new);
        instructor.setUserName(updateInstructorRequest.getUserName());
        instructor.setUserSurname(updateInstructorRequest.getUserSurname());
        instructor.setIdentityNumber(updateInstructorRequest.getIdentityNumber());
        instructor.setUserMail(updateInstructorRequest.getUserMail());
        String photoPath = "";
        if (updateInstructorRequest.getImageBase64() != null){
            photoPath = fileService.saveFile(instructor,updateInstructorRequest.getImageBase64());
        }
        Instructor savedInstructor = instructorRepository.save(instructor);
        return modelMapperService.entityToDto(savedInstructor,InstructorDto.class);
    }
}
