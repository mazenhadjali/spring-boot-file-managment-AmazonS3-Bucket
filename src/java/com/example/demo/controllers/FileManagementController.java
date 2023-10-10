package com.example.demo.controllers;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.demo.entity.FileAttachment;
import com.example.demo.services.FileAttachmentService;
import com.example.demo.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin
public class FileManagementController {

    private final FileManagementService fileManagementService;
    private final FileAttachmentService fileAttachmentService;


    // Upload a file
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                             @RequestParam("bucketName") String bucketName) {
        if (multipartFile.isEmpty()){
            return ResponseEntity.badRequest().body("Missing file");
        }

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
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        S3Object s3Object = fileManagementService.getFileById(id);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            byte[] content = IOUtils.toByteArray(inputStream);

            System.out.println(s3Object.getKey());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", s3Object.getKey());

            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            inputStream.close();
        }
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
