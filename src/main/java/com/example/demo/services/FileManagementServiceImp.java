package com.example.demo.services;

import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.entity.FileAttachment;
import com.example.demo.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileManagementServiceImp implements FileManagementService {

    private final AmazonS3Service amazonS3Service;
    private final FileAttachmentService fileAttachmentService;

    // Save a file to both S3 bucket and FileAttachment table
    @Override
    @SneakyThrows
    public FileAttachment saveFile(MultipartFile multipartFile, String bucketName) {

        String fileName = multipartFile.getOriginalFilename();
        String objectKey = CommonUtils.generateUUID() + "_" + System.currentTimeMillis() + '-' + fileName;

        File tempFile = File.createTempFile("temp-" + System.currentTimeMillis(), '.' + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(tempFile.toPath());
        // Upload the file to S3 bucket
        amazonS3Service.uploadFile(bucketName, objectKey, tempFile);
        // saving the gile Attachment
        FileAttachment fileAttachment = FileAttachment.builder()
                .bucketName(bucketName)
                .fileName(fileName)
                .fileSize(multipartFile.getSize())
                .contentType(multipartFile.getContentType())
                .objectName(objectKey)
                .build();
        tempFile.delete();
        return fileAttachmentService.saveFileAttachment(fileAttachment);
    }

    @Override
    @SneakyThrows
    public S3Object getFileById(Long id) {
        Optional<FileAttachment> optionalFileAttachment = fileAttachmentService.getFileAttachmentById(id);
        if (optionalFileAttachment.isPresent()) {
            FileAttachment fileAttachment = optionalFileAttachment.get();
            return amazonS3Service.downloadFile(fileAttachment.getBucketName(), fileAttachment.getObjectName());
        }
        return null;
    }

    @Override
    @SneakyThrows
    public boolean deleteFile(Long id) {
        Optional<FileAttachment> optionalFileAttachment = fileAttachmentService.getFileAttachmentById(id);
        if (optionalFileAttachment.isPresent()) {
            FileAttachment fileAttachment = optionalFileAttachment.get();
            // Delete the file from S3 using its objectName
            amazonS3Service.deleteFile(fileAttachment.getBucketName(), fileAttachment.getObjectName());
            // Delete the FileAttachment from the database
            fileAttachmentService.deleteFileAttachmentById(id);
            return true;
        }
        return false;
    }
}
