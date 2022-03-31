package com.hst.metagen.controller;

import com.hst.metagen.dto.StudentDto;
import com.hst.metagen.entity.Student;
import com.hst.metagen.service.FileService;
import com.hst.metagen.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private final FileService fileService;

    @PostMapping(consumes = {APPLICATION_JSON_VALUE,MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveStudent(@RequestPart("studentDto") StudentDto studentDto, @RequestPart(value = "file") MultipartFile files) throws IOException {
        return ResponseEntity.ok(studentService.save(studentDto,files));
    }

    @GetMapping("/test")
    public Student getTest(long id){
        return studentService.getStudent(id);
    }



}
