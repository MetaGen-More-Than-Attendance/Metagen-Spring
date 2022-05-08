package com.hst.metagen.api.controller;

import com.hst.metagen.service.abstracts.LectureService;
import com.hst.metagen.service.requests.CreateLectureRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/lecture")
public class LectureController {
    private final LectureService lectureService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveLecture(@RequestBody CreateLectureRequest createLectureRequest) {
        return ResponseEntity.ok(lectureService.save(createLectureRequest));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getLecture(@RequestParam Long lectureId) {
        return ResponseEntity.ok(lectureService.getById(lectureId));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getLectures() {
        return ResponseEntity.ok(lectureService.getAllLectures());
    }
}
