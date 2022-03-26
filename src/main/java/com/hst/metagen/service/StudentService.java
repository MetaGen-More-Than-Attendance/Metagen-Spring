package com.hst.metagen.service;

import org.springframework.web.multipart.MultipartFile;

public interface StudentService {
    void savePhoto(MultipartFile file);

}
