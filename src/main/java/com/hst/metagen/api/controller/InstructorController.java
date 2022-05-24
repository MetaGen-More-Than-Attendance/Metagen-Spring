package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.InstructorService;
import com.hst.metagen.service.requests.instructor.CreateInstructorRequest;
import com.hst.metagen.service.requests.instructor.UpdateInstructorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        return a;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInstructor() {
        return ResponseEntity.ok(instructorService.getAllInstructor());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInstructor(@RequestParam Long instructorId) {
        return ResponseEntity.ok(instructorService.deleteInstructor(instructorId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInstructor(@RequestParam Long instructorId,@RequestBody UpdateInstructorRequest updateInstructorRequest) throws IOException {
        return ResponseEntity.ok(instructorService.update(instructorId,updateInstructorRequest));
    }

}
