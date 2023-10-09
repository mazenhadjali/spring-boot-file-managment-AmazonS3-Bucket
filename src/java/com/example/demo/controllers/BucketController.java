package com.example.demo.controllers;

import com.example.demo.services.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buckets")
@RequiredArgsConstructor
@CrossOrigin
public class BucketController {

    private final AmazonS3Service amazonS3Service;

    @PostMapping
    public ResponseEntity<String> createBucket (@RequestParam("bucketName") String bucketName){
        if (amazonS3Service.createBucket(bucketName)){
            return ResponseEntity.ok(String.format("Bucket: '%s' Created",bucketName));
        }else {
            return ResponseEntity.status(403).body(String.format("Bucket: '%s' already exists.",bucketName));
        }
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllBuckets(){
        return ResponseEntity.ok().body(amazonS3Service.getAllBuckets());
    }

}
