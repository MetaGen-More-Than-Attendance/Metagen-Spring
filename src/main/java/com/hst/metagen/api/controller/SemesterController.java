package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.SemesterService;;
import com.hst.metagen.service.requests.semester.CreateSemesterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/semester")
public class SemesterController {

    private final SemesterService semesterService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveSemester(@RequestBody CreateSemesterRequest createSemesterRequest) {
        return ResponseEntity.ok(semesterService.save(createSemesterRequest));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllSemester() {
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }

}
