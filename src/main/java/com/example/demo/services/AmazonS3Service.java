package com.example.demo.services;

import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;

public interface AmazonS3Service {

    CreateBucketResponse createBucket(String bucketName);

    DeleteBucketResponse deleteEmptyBucket(String bucketName) ;

    PutObjectResponse putObject(String bucketName, String key, byte[] file);

    byte[] getObject(String bucketName, String objectKey);

    DeleteObjectsResponse deleteMultipleFiles(ArrayList<ObjectIdentifier> keys, String bucketName);


}
