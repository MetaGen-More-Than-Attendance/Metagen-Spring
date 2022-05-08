package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.DepartmentService;
import com.hst.metagen.service.requests.CreateDepartmentRequest;
import com.hst.metagen.service.requests.CreateLectureRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
