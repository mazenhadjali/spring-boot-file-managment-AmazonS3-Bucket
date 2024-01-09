package com.example.demo.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateBucketRequest {
    private String bucketName;
}
