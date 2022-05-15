package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.StudentService;
import com.hst.metagen.service.requests.instructor.UpdateInstructorRequest;
import com.hst.metagen.service.requests.student.CreateStudentRequest;
import com.hst.metagen.service.requests.student.UpdateStudentRequest;
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

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveStudent(@RequestBody CreateStudentRequest createStudentRequest) throws IOException {
        return ResponseEntity.ok(studentService.save(createStudentRequest));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getStudent(@RequestParam Long studentId) {
        return ResponseEntity.ok(studentService.getStudent(studentId));
    }

    @GetMapping("/get-photo")
    public byte[] getStudentPhoto(@RequestParam Long studentId) throws IOException {
        byte[] a= studentService.getStudentPhoto(studentId);
        System.out.println(a.length);
        return a;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStudent(@RequestParam Long studentId) {
        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStudent(@RequestParam Long studentId,@RequestBody UpdateStudentRequest updateStudentRequest) throws IOException {
        return ResponseEntity.ok(studentService.update(studentId,updateStudentRequest));
    }

    @GetMapping("/{studentId}/lecture")
    public ResponseEntity<?> getAllLectures(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentLectures(studentId));
    }

    @GetMapping("/getAllByDepartment")
    public ResponseEntity<?> getAllStudentByDepartmentId(@RequestParam Long departmentId) {
        return ResponseEntity.ok(studentService.getAllByDepartment(departmentId));
    }

}
