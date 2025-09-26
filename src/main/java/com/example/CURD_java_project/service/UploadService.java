package com.example.CURD_java_project.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public String handleSaveUploadFile(MultipartFile file, String nameFolder);
}
