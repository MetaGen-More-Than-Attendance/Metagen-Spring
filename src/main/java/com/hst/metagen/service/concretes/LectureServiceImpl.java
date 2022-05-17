package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Department;
import com.hst.metagen.entity.Instructor;
import com.hst.metagen.entity.Lecture;
import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.LectureRepository;
import com.hst.metagen.service.abstracts.DepartmentService;
import com.hst.metagen.service.abstracts.InstructorService;
import com.hst.metagen.service.abstracts.LectureService;
import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.dtos.LectureDto;
import com.hst.metagen.service.requests.lecture.CreateLectureRequest;
import com.hst.metagen.util.exception.NotFoundException;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    private final InstructorService instructorService;

    private final DepartmentService departmentService;

    private final ModelMapperService modelMapperService;

    private final StudentService studentService;
    @Override
    public LectureDto save(CreateLectureRequest createLectureRequest) {
        Lecture lecture = modelMapperService.dtoToEntity(createLectureRequest,Lecture.class);
        Instructor instructor = instructorService.getInstructorEntity(createLectureRequest.getInstructorId());
        Department department = departmentService.getDepartmentEntity(createLectureRequest.getDepartmentId());
        lecture.setInstructor(instructor);
        lecture.setDepartment(department);
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
            if (Objects.nonNull(lecture.getInstructor())){
                lectureDto.setInstructorId(lecture.getInstructor().getInstructorId());
            }
            return lectureDto;
        }).collect(Collectors.toList());
        return lectureDtos;
    }

    @Override
    public LectureDto update(Long lectureId,CreateLectureRequest createLectureRequest) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(NotFoundException::new);
        if (createLectureRequest.getInstructorId() != null){
            Instructor instructor = modelMapperService.dtoToEntity(instructorService.getInstructor(createLectureRequest.getInstructorId()),Instructor.class);
            lecture.setInstructor(instructor);
        }
        if (createLectureRequest.getDepartmentId() != null){
            Department department = modelMapperService.dtoToEntity(departmentService.findById(createLectureRequest.getDepartmentId()),Department.class);
            lecture.setDepartment(department);
        }
        lecture.setLectureName(createLectureRequest.getLectureName());
        lecture.setLectureStartDate(createLectureRequest.getLectureStartDate());
        return modelMapperService.entityToDto(lectureRepository.save(lecture),LectureDto.class);
    }

    @Override
    public Boolean delete(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(NotFoundException::new);
        lectureRepository.delete(lecture);
        return true;
    }

    @Override
    public LectureDto addStudent(Long lectureId, List<Long> studentIds) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(NotFoundException::new);
        List<Student> studentList = studentIds.stream().map(s-> {
            Student student = modelMapperService.dtoToEntity(studentService.getStudent(s),Student.class);
            student.setUserId(student.getStudentId());
            student.getStudentLectures().add(lecture);
            return student;
        }).collect(Collectors.toList());

        if (lecture.getLectureStudents() != null) {
            lecture.getLectureStudents().addAll(studentList);
        } else {
            lecture.setLectureStudents(studentList);
        }

        LectureDto lectureDto = modelMapperService.entityToDto(lectureRepository.save(lecture),LectureDto.class);
        if (lecture.getInstructor().getInstructorId() != null){
            lectureDto.setInstructorId(lecture.getInstructor().getInstructorId());
        }
        List<Long> studentIdList = new ArrayList<>();
        if (lecture.getLectureStudents() != null){
            for (Student student : lecture.getLectureStudents()){
                studentIdList.add(student.getStudentId());
            }
            lectureDto.setStudentIds(studentIdList);
        }

        return lectureDto;
    }

    @Override
    public Lecture getLectureEntity(Long lectureId) {
        return lectureRepository.getById(lectureId);
    }

    @Override
    public List<LectureDto> getInstructorLectures(Long instructorId) {
        List<Lecture> lectures = lectureRepository.findLecturesByInstructor_InstructorId(instructorId);
        return modelMapperService.entityToDtoList(lectures,LectureDto.class);

    }
}
