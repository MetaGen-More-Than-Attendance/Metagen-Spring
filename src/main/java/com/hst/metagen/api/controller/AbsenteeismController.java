package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.AbsenteeismService;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import com.hst.metagen.service.requests.absenteeism.UpdateAbsenteeismRequest;
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

    @PutMapping(value = "/updateAbseenteism")
    public ResponseEntity<?> updateAbseenteism(@RequestBody UpdateAbsenteeismRequest updateAbsenteeismRequest) {
        return ResponseEntity.ok(absenteeismService.update(updateAbsenteeismRequest));
    }

    @GetMapping(value = "/getAbseenteismByStudentIdAndLectureId")
    public ResponseEntity<?> getAbseenteismByStudentIdAndLectureId(@RequestParam Long lectureId,@RequestParam Long studentId) {
        return ResponseEntity.ok(absenteeismService.getStudentAndLectureAbsenteeisms(studentId,lectureId));
    }

    @DeleteMapping(value = "/deleteAllAbseenteism")
    public ResponseEntity<?> deleteAll() {
        absenteeismService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
