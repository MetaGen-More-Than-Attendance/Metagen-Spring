package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.AbsenteeismService;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import com.hst.metagen.service.requests.absenteeism.UpdateAbsenteeismRequest;
import com.hst.metagen.service.requests.department.CreateDepartmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    public ResponseEntity<?> getAbseenteismByStudentIdAndLectureId(@RequestParam Long lectureId,@RequestParam Long studentId,@RequestParam Long semesterId) {
        return ResponseEntity.ok(absenteeismService.getStudentAndLectureAbsenteeisms(studentId,lectureId, semesterId));
    }

    @GetMapping(value = "/getAbseenteismLectureIdandAbsenteeismDate")
    public ResponseEntity<?> getLectureAbsenteesimsOnDate(@RequestParam Long lectureId,@RequestParam String localDateString) {
        LocalDate localDate = LocalDate.parse(localDateString);
        return ResponseEntity.ok(absenteeismService.getLectureAbsenteesimsOnDate(lectureId, localDate));
    }

    @GetMapping(value = "/getAbseenteismByLectureId")
    public ResponseEntity<?> getLectureAbsenteesims(@RequestParam Long lectureId, @RequestParam Long semesterId) {
        return ResponseEntity.ok(absenteeismService.getLectureAbsenteesims(lectureId, semesterId));
    }

    @DeleteMapping(value = "/deleteAllAbseenteism")
    public ResponseEntity<?> deleteAll() {
        absenteeismService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
