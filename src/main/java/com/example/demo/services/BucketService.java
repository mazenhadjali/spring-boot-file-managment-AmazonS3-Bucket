package com.example.demo.services;

import com.example.demo.dtos.BucketDto;
import com.example.demo.entity.Bucket;

import java.util.List;

public interface BucketService {

    Bucket createBucket(String bucketName);

    List<BucketDto> getAllBuckets();

    void deleteBucket(String bucketName);
}
