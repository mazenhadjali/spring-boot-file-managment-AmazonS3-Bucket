package com.example.demo.services;

import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.entity.FileAttachment;
import org.springframework.web.multipart.MultipartFile;

public interface FileManagementService {

    FileAttachment saveFile(MultipartFile multipartFile, String bucketName);

    S3Object getFileById(Long id);

    boolean deleteFile(Long id);
}
