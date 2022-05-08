package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.DepartmentService;
import com.hst.metagen.service.requests.department.CreateDepartmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveDepartment(@RequestBody CreateDepartmentRequest createDepartmentRequest) {
        return ResponseEntity.ok(departmentService.save(createDepartmentRequest));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllDepartment() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStudent(@RequestParam Long departmentId) {
        return ResponseEntity.ok(departmentService.deleteDepartments(departmentId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDepartment(@RequestParam Long departmentId,@RequestBody CreateDepartmentRequest createDepartmentRequest) throws IOException {
        return ResponseEntity.ok(departmentService.update(departmentId,createDepartmentRequest));
    }
}
