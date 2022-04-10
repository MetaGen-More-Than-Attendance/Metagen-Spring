package com.hst.metagen.controller;

import com.hst.metagen.service.abstracts.FileService;
import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.requests.CreateStudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private final FileService fileService;

    @PostMapping(value = "/register",consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveStudent(@RequestPart(value = "studentDto") CreateStudentDto studentDto, @RequestParam(value = "file")MultipartFile file) throws IOException {
        return ResponseEntity.ok(studentService.save(studentDto,file));
    }

    @PostMapping("/get-photo")
    public byte[] getStudentPhoto(@RequestParam Long id) throws IOException {
        byte[] a= fileService.getFile(id);
        System.out.println(a.length);
        return a;
    }
}
