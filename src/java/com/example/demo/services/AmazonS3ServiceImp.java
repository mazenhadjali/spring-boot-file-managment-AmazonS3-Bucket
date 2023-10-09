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
        return  amazonS3.listBuckets().stream()
                .map(Bucket::getName)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public boolean createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
            return true;
        }
        return false;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        return amazonS3.doesBucketExistV2(bucketName);
    }

    // File operations

    @Override
    @SneakyThrows
    public boolean uploadFile(String bucketName, String objectKey, File file) {
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, file));
            return true;
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            return false;
        }
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
