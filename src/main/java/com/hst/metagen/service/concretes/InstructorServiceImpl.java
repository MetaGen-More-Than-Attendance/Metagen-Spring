package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Instructor;
import com.hst.metagen.entity.Role;
import com.hst.metagen.repository.InstructorRepository;
import com.hst.metagen.service.abstracts.FileService;
import com.hst.metagen.service.abstracts.InstructorService;
import com.hst.metagen.service.abstracts.RoleService;
import com.hst.metagen.service.dtos.InstructorDto;
import com.hst.metagen.service.requests.CreateInstructorRequest;
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

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final ModelMapperService modelMapperService;
    private final RoleService roleService;
    private final PasswordEncoder bcryptEncoder;
    private final InstructorRepository instructorRepository;
    private final FileService fileService;

    @Override
    public InstructorDto save(CreateInstructorRequest createInstructorRequest) throws IOException {
        Role instructorUser = roleService.getByRoleName("TEACHER_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(instructorUser);
        Instructor instructor = modelMapperService.forRequest().map(createInstructorRequest, Instructor.class);

        if (createInstructorRequest.getImageBase64()!=null){
            instructor = fileService.saveFile(instructor,createInstructorRequest.getImageBase64());
        }

        instructor.setUserPassword(bcryptEncoder.encode(createInstructorRequest.getUserPassword()));
        instructor.setUserRoles(roles);

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
}
