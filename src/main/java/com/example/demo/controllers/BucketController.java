package com.example.demo.controllers;

import com.amazonaws.services.s3.model.Bucket;
import com.example.demo.services.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/buckets")
@RequiredArgsConstructor
@CrossOrigin
public class BucketController {

    private final AmazonS3Service amazonS3Service;

    @PostMapping
    public ResponseEntity<Object> createBucket (@RequestParam("bucketName") String bucketName){
        Bucket bucket = amazonS3Service.createBucket(bucketName);
        return ResponseEntity.ok(bucket);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllBuckets(){
        return ResponseEntity.ok().body(amazonS3Service.getAllBuckets());
    }

}
