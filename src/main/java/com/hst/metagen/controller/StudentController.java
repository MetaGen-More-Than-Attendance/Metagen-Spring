package com.hst.metagen.controller;

import com.hst.metagen.entity.Student;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@RestController
@CrossOrigin("*")
public class StudentController {

    @PostMapping("/camera")
    private String getCamera(@RequestBody String imageSrc){
        System.out.println("TEST");
        String uri = "http://127.0.0.1:5000/api";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri,imageSrc, String.class);
        System.out.println(result);
        return result;
    }

    @PostMapping(path = "/attachments", consumes = {MULTIPART_FORM_DATA_VALUE})
    public byte[] handleFileUpload(@RequestParam(value = "file") MultipartFile files) throws IOException {

        Student student = new Student();
        student.setFacePhoto(files.getBytes());
        System.out.println(student.getFacePhoto());
        return student.getFacePhoto();
    }

    @GetMapping("/test")
    public String getTest(){
        System.out.println("success");
        return "succesful";
    }



}
