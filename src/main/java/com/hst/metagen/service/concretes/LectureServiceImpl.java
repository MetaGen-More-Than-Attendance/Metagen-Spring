package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Instructor;
import com.hst.metagen.entity.Lecture;
import com.hst.metagen.repository.LectureRepository;
import com.hst.metagen.service.abstracts.InstructorService;
import com.hst.metagen.service.abstracts.LectureService;
import com.hst.metagen.service.dtos.LectureDto;
import com.hst.metagen.service.requests.CreateLectureRequest;
import com.hst.metagen.util.exception.NotFoundException;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    private final InstructorService instructorService;

    private final ModelMapperService modelMapperService;

    @Override
    public LectureDto save(CreateLectureRequest createLectureRequest) {
        Lecture lecture = modelMapperService.dtoToEntity(createLectureRequest,Lecture.class);
        Instructor instructor = modelMapperService.forDto().map(instructorService.getInstructor(createLectureRequest.getInstructorId()),Instructor.class);
        if(instructor != null)
            lecture.setInstructor(instructor);
        return modelMapperService.entityToDto(lectureRepository.save(lecture),LectureDto.class);
    }

    @Override
    public LectureDto getById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(NotFoundException::new);
        LectureDto lectureDto = modelMapperService.entityToDto(lecture,LectureDto.class);
        if (lecture.getInstructor() != null){
            lectureDto.setInstructorId(lecture.getInstructor().getInstructorId());
        }
        return lectureDto;
    }

    @Override
    public List<LectureDto> getStudentLectures(Long studentId) {
        List<LectureDto> lectureDtos = modelMapperService.entityToDtoList(lectureRepository.findAll(),LectureDto.class);
        return lectureDtos;
    }

    @Override
    public List<LectureDto> getAllLectures() {
        List<LectureDto> lectureDtos = lectureRepository.findAll().stream().map(lecture -> {
            LectureDto lectureDto =  modelMapperService.entityToDto(lecture,LectureDto.class);
            lectureDto.setInstructorId(lecture.getInstructor().getInstructorId());
            return lectureDto;
        }).collect(Collectors.toList());
        return lectureDtos;
    }
}
