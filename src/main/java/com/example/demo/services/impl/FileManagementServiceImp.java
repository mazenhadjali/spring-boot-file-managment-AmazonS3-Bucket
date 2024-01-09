package com.example.demo.services.impl;

import com.example.demo.entity.Bucket;
import com.example.demo.entity.FileAttachment;
import com.example.demo.repositories.BucketRepositoriy;
import com.example.demo.services.AmazonS3Service;
import com.example.demo.services.FileAttachmentService;
import com.example.demo.services.FileManagementService;
import com.example.demo.utils.CommonUtils;
import com.example.demo.utils.FilewithFileAttachment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileManagementServiceImp implements FileManagementService {

    private final AmazonS3Service amazonS3Service;
    private final FileAttachmentService fileAttachmentService;
    private final BucketRepositoriy bucketRepositoriy;

    @Override
    @SneakyThrows
    public FileAttachment saveFile(MultipartFile multipartFile, String bucketName) {
        Bucket bucket = bucketRepositoriy.findByName(bucketName).orElseThrow(() -> new RuntimeException("Bucket with name %s not found".formatted(bucketName)));
        String fileName = multipartFile.getOriginalFilename();
        String objectKey = CommonUtils.generateUUID() + "_" + System.currentTimeMillis() + '-' + fileName;
        amazonS3Service.putObject(bucketName, objectKey, multipartFile.getBytes());
        FileAttachment fileAttachment = new FileAttachment(fileName, objectKey, multipartFile.getSize(), multipartFile.getContentType(), bucket);
        return fileAttachmentService.saveFileAttachment(fileAttachment);
    }

    @Override
    @SneakyThrows
    public FilewithFileAttachment getFileById(Long id) {
        FileAttachment fileAttachment = fileAttachmentService.getFileAttachmentById(id).orElseThrow(() -> new RuntimeException("no file with Id %d".formatted(id)));
        return new FilewithFileAttachment(amazonS3Service.getObject(fileAttachment.getBucket().getName(), fileAttachment.getObjectKey()), fileAttachment);
    }

    @Override
    @SneakyThrows
    public boolean deleteFile(Long id) {
        FileAttachment fileAttachment = fileAttachmentService.getFileAttachmentById(id).orElse(null);
        if (fileAttachment == null)
            return false;
        ArrayList<ObjectIdentifier> identifiers = new ArrayList<>();
        identifiers.add(ObjectIdentifier.builder().key(fileAttachment.getObjectKey()).build());
        amazonS3Service.deleteMultipleFiles(identifiers, fileAttachment.getBucket().getName());
        fileAttachmentService.deleteFileAttachmentById(fileAttachment.getId());
        return true;
    }

    @Override
    public void deleteMultipleFilesByIds(List<Long> ids) {
        for (Long id : ids) {
            deleteFile(id);
        }
    }
}
