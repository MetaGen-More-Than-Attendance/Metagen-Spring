package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Instructor;
import com.hst.metagen.service.dtos.InstructorDto;
import com.hst.metagen.service.requests.instructor.CreateInstructorRequest;
import com.hst.metagen.service.requests.instructor.UpdateInstructorRequest;

import java.io.IOException;
import java.util.List;

public interface InstructorService {
    InstructorDto save(CreateInstructorRequest createInstructorRequest) throws IOException;
    InstructorDto getInstructor(Long instructorId);
    byte[] getInstructorPhoto(Long instructorId) throws IOException;
    List<InstructorDto> getAllInstructor();
    Boolean deleteInstructor(Long instructorId);
    InstructorDto update(Long instructorId, UpdateInstructorRequest updateInstructorRequest) throws IOException;
    Instructor getInstructorEntity(Long instructorId);
    String getInstructorPhotoBase64(Long instructorId) throws IOException;
}
