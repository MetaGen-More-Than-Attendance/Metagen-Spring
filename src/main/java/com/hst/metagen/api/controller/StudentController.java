package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.CreateStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveStudent(@RequestBody CreateStudentRequest createStudentRequest) throws IOException {
        return ResponseEntity.ok(studentService.save(createStudentRequest));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getStudent(@RequestParam Long studentId) {
        return ResponseEntity.ok(studentService.getStudent(studentId));
    }

    @PostMapping("/get-photo")
    public byte[] getStudentPhoto(@RequestParam Long studentId) throws IOException {
        byte[] a= studentService.getStudentPhoto(studentId);
        System.out.println(a.length);
        return a;
    }

    @GetMapping("get-all")
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }
}
