package com.example.demo.services.impl;

import com.example.demo.services.AmazonS3Service;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class AmazonS3ServiceImp implements AmazonS3Service {

    private final S3Client s3;

    @Override
    public CreateBucketResponse createBucket(String bucketName) {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();
        return s3.createBucket(bucketRequest);
    }

    @Override
    public DeleteBucketResponse deleteEmptyBucket(String bucketName) {
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build();
        return s3.deleteBucket(deleteBucketRequest);
    }


    @Override
    public PutObjectResponse putObject(String bucketName, String key, byte[] file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        return s3.putObject(objectRequest, RequestBody.fromBytes(file));
    }

    @Override
    public byte[] getObject(String bucketName, String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        ResponseInputStream<GetObjectResponse> res = s3.getObject(getObjectRequest);

        try {
            return res.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public DeleteObjectsResponse deleteMultipleFiles(ArrayList<ObjectIdentifier> keys, String bucketName) {
        Delete del = Delete.builder()
                .objects(keys)
                .build();
        DeleteObjectsRequest multiObjectDeleteRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(del)
                .build();
        return s3.deleteObjects(multiObjectDeleteRequest);
    }

}
