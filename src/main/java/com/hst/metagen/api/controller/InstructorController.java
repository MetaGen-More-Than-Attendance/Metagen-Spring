package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.InstructorService;
import com.hst.metagen.service.dtos.InstructorDto;
import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.service.requests.CreateInstructorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveInstructor(@RequestBody CreateInstructorRequest createInstructorRequest) throws IOException {
        return ResponseEntity.ok(instructorService.save(createInstructorRequest));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getInstructor(@RequestParam Long instructorId) {
        return ResponseEntity.ok(instructorService.getInstructor(instructorId));
    }

    @PostMapping("/get-photo")
    public byte[] getInstructorPhoto(@RequestParam Long instructorId) throws IOException {
        byte[] a= instructorService.getInstructorPhoto(instructorId);
        System.out.println(a.length);
        return a;
    }

    @GetMapping("get-all")
    public List<InstructorDto> getAllInstructor() {
        return instructorService.getAllInstructor();
    }

}
