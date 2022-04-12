package com.hst.metagen.controller;

import com.hst.metagen.service.abstracts.FileService;
import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.requests.CreateStudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final FileService fileService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveStudent(@RequestBody CreateStudentDto studentDto) throws IOException {

        return ResponseEntity.ok(studentService.save(studentDto));
    }

    @PostMapping("/get-photo")
    public byte[] getStudentPhoto(@RequestParam Long id) throws IOException {
        byte[] a= fileService.getFile(id);
        System.out.println(a.length);
        return a;
    }
}
