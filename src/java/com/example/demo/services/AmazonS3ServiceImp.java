package com.example.demo.services;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AmazonS3ServiceImp implements AmazonS3Service {

    private final AmazonS3 amazonS3;

    // Bucket operations

    public List<String> getAllBuckets() {
        return amazonS3.listBuckets().stream()
                .map(Bucket::getName)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public Bucket createBucket(String bucketName) {
        return amazonS3.createBucket(bucketName);
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        return amazonS3.doesBucketExistV2(bucketName);
    }

    // File operations

    @Override
    @SneakyThrows
    public PutObjectResult uploadFile(String bucketName, String objectKey, File file) {
        return amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, file));
    }

    @Override
    @SneakyThrows
    public boolean deleteFile(String bucketName, String objectKey) {
        try {
            amazonS3.deleteObject(bucketName, objectKey);
            return true;
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    @SneakyThrows
    public S3Object downloadFile(String bucketName, String objectKey) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectKey);
        return amazonS3.getObject(getObjectRequest);
    }

}
