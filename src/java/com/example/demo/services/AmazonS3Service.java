package com.example.demo.services;

import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.util.List;

public interface AmazonS3Service {

    // Bucket operations

    List<String> getAllBuckets();

    boolean createBucket(String bucketName);

    boolean doesBucketExist(String bucketName);

    // File operations

    boolean uploadFile(String bucketName, String objectKey, File file);

    boolean deleteFile(String bucketName, String objectKey);

    S3Object downloadFile(String bucketName, String objectKey);

}
