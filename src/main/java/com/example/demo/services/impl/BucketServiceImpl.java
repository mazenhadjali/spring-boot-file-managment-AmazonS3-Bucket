package com.example.demo.services.impl;

import com.example.demo.dtos.BucketDto;
import com.example.demo.entity.Bucket;
import com.example.demo.repositories.BucketRepositoriy;
import com.example.demo.services.AmazonS3Service;
import com.example.demo.services.BucketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final BucketRepositoriy bucketRepositoriy;
    private final ModelMapper modelMapper;
    private final AmazonS3Service amazonS3Service;

    @Override
    public Bucket createBucket(String bucketName) {
        amazonS3Service.createBucket(bucketName);
        return bucketRepositoriy.save(new Bucket(bucketName));
    }

    @Override
    public List<BucketDto> getAllBuckets() {
        return bucketRepositoriy.findAll().stream().map(bucket -> modelMapper.map(bucket, BucketDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteBucket(String bucketName) {
        amazonS3Service.deleteEmptyBucket(bucketName);
        bucketRepositoriy.deleteBucketByName(bucketName);
    }
}
