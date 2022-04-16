package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.InstructorDto;
import com.hst.metagen.service.requests.CreateInstructorRequest;

import java.io.IOException;

public interface InstructorService {
    InstructorDto save(CreateInstructorRequest createInstructorRequest) throws IOException;
    InstructorDto getInstructor(Long instructorId);
    byte[] getInstructorPhoto(Long instructorId) throws IOException;
}
