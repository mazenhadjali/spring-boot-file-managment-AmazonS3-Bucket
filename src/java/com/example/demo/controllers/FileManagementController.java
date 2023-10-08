package com.example.demo.controllers;

import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.entity.FileAttachment;
import com.example.demo.services.FileAttachmentService;
import com.example.demo.services.FileManagementService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileManagementController {

    private final FileManagementService fileManagementService;
    private final FileAttachmentService fileAttachmentService;


    // Upload a file
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                             @RequestParam("bucketName") String bucketName) {
        FileAttachment savedFile = fileManagementService.saveFile(multipartFile, bucketName);
        if (savedFile != null) {
            return ResponseEntity.ok().body(savedFile);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
    }

    // Download a file by ID
    @SneakyThrows
    @GetMapping("/download/{id}")
    public void downloadFile(@PathVariable("id") Long id, HttpServletResponse response) {
        System.out.println(id);
        S3Object s3Object = fileManagementService.getFileById(id);
        InputStream inputStream = s3Object.getObjectContent();

        response.setHeader("Content-Disposition", "attachment; fileName="+s3Object.getKey());
        response.setContentType(Objects.requireNonNull(s3Object.getObjectMetadata().getContentType()));

        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        inputStream.close();
    }

    // Delete a file by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        boolean deleted = fileManagementService.deleteFile(id);
        if (deleted) {
            return ResponseEntity.ok("File deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bucket/{bucketName}")
    @SneakyThrows
    public ResponseEntity<List<FileAttachment>> getFileAttachmentsByBucketName(@PathVariable("bucketName") String bucketName) {
        return ResponseEntity.ok().body(fileAttachmentService.getAllFileAttachments(bucketName));
    }
}
