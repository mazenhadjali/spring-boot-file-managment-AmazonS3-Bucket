package com.example.demo.services;

import com.example.demo.entity.FileAttachment;

import java.util.List;
import java.util.Optional;

public interface FileAttachmentService {


    FileAttachment saveFileAttachment(FileAttachment fileAttachment);
    public List<FileAttachment> getAllFileAttachments(String bucketName);
    Optional<FileAttachment> getFileAttachmentById(Long id);
    void deleteFileAttachmentById(Long id);

}
