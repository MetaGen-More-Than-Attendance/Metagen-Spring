package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.AbsenteeismService;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import com.hst.metagen.service.requests.department.CreateDepartmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/absenteeism")
public class AbsenteeismController {

    private final AbsenteeismService absenteeismService;

    @PostMapping(value = "/createDefaultAbseenteism")
    public ResponseEntity<?> createDefaultAbseenteism(@RequestBody CreateAbsenteeismRequest createAbsenteeismRequest) {
        absenteeismService.save(createAbsenteeismRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
