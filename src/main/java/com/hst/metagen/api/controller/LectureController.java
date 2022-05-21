package com.hst.metagen.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hst.metagen.service.abstracts.LectureService;
import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.department.CreateDepartmentRequest;
import com.hst.metagen.service.requests.lecture.CreateLectureRequest;
import com.hst.metagen.service.requests.student.AddStudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/lecture")
public class LectureController {
    private final LectureService lectureService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveLecture(@RequestBody CreateLectureRequest createLectureRequest) {
        return ResponseEntity.ok(lectureService.save(createLectureRequest));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getLecture(@RequestParam Long lectureId) {
        return ResponseEntity.ok(lectureService.getById(lectureId));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getLectures() {
        return ResponseEntity.ok(lectureService.getAllLectures());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLecture(@RequestParam Long lectureId) {
        return ResponseEntity.ok(lectureService.delete(lectureId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLecture(@RequestParam Long lectureId,@RequestBody CreateLectureRequest createLectureRequest) throws IOException {
        return ResponseEntity.ok(lectureService.update(lectureId,createLectureRequest));
    }

    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(@RequestParam Long lectureId, @RequestBody AddStudentDto addStudentDto) throws IOException {
        return ResponseEntity.ok(lectureService.addStudent(lectureId,addStudentDto.getStudentIds()));
    }

    @GetMapping("/getInstructorLectures")
    public ResponseEntity<?> getLecturesByInstructorId(@RequestParam Long instructorId) throws IOException {
        return ResponseEntity.ok(lectureService.getInstructorLectures(instructorId));
    }

    @GetMapping("/getLectureStudents")
    public ResponseEntity<?> getLectureStudents(Long lectureId) {
        return ResponseEntity.ok(lectureService.getLectureStudents(lectureId));
    }

}
