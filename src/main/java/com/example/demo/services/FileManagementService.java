package com.example.demo.services;

import com.example.demo.entity.FileAttachment;
import com.example.demo.utils.FilewithFileAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileManagementService {

    FileAttachment saveFile(MultipartFile multipartFile, String bucketName);

    FilewithFileAttachment getFileById(Long id);

    boolean deleteFile(Long id);

    void deleteMultipleFilesByIds(List<Long> ids);
}
