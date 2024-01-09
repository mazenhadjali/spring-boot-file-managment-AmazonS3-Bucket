package com.example.demo.controllers;

import com.example.demo.dtos.BucketDto;
import com.example.demo.dtos.CreateBucketRequest;
import com.example.demo.dtos.DeleteMultiple;
import com.example.demo.dtos.FileAttachmentDto;
import com.example.demo.entity.Bucket;
import com.example.demo.entity.FileAttachment;
import com.example.demo.services.BucketService;
import com.example.demo.services.FileAttachmentService;
import com.example.demo.services.FileManagementService;
import com.example.demo.utils.FilewithFileAttachment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@CrossOrigin
public class FileManagementController {

    private final FileManagementService fileManagementService;

    private final FileAttachmentService fileAttachmentService;
    private final BucketService bucketService;

    @GetMapping("/buckets")
    @SneakyThrows
    public ResponseEntity<List<BucketDto>> getAllBuckets() {
        return ResponseEntity.ok().body(bucketService.getAllBuckets());
    }

    @PostMapping("/buckets")
    @SneakyThrows
    public ResponseEntity<Bucket> createNewBucket(@RequestBody CreateBucketRequest createBucketRequest) {
        return ResponseEntity.ok().body(bucketService.createBucket(createBucketRequest.getBucketName()));
    }

    @GetMapping("/buckets/{bucketName}")
    @SneakyThrows
    public ResponseEntity<List<FileAttachmentDto>> getFileAttachmentsByBucketName(@PathVariable("bucketName") String bucketName) {
        return ResponseEntity.ok().body(fileAttachmentService.getAllFileAttachments(bucketName));
    }

    // Upload a file in a bucket
    @PostMapping("/upload")
    public ResponseEntity<FileAttachment> uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("bucketName") String bucketName) {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("Missing file");
        }
        return ResponseEntity.ok().body(fileManagementService.saveFile(multipartFile, bucketName));
    }

    // Download a file by ID
    @SneakyThrows
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        // Retrieve file details from your service
        FilewithFileAttachment details = fileManagementService.getFileById(id);
        FileAttachment fileAttachment = details.getFileAttachment();
        // Create headers to set the file's MIME type and suggest a file name
        String mimeType = fileAttachment.getContentType();
        String filename = fileAttachment.getFileName();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mimeType));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(details.getFile(), headers, HttpStatus.OK);
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

    @DeleteMapping("/deletemultiple")
    public ResponseEntity<String> deleteFile(@RequestBody DeleteMultiple deleteMultiple) {
        fileManagementService.deleteMultipleFilesByIds(deleteMultiple.getIds());
        return ResponseEntity.ok("Files Deleted Successfully");
    }


}
