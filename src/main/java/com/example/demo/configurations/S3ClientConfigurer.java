package com.example.demo.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class S3ClientConfigurer {

    @Value("${minio.minioEndpoint}")
    private String minioEndpoint;

    @Value("${minio.minioAccessKey}")
    private String minioAccessKey;

    @Value("${minio.minioSecretKey}")
    private String minioSecretKey;

    @Bean
    public S3Client amazonS3() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(minioAccessKey, minioSecretKey);


        S3Configuration s3Config = S3Configuration.builder()
                .pathStyleAccessEnabled(true) // Enable path-style access for compatibility
                .build();

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create(minioEndpoint)) // Override the S3 endpoint to use MinIO's
                .region(Region.of("us-east-1")) // Dummy region, replace with your minio instance's region
                .serviceConfiguration(s3Config) // Apply path-style access configuration
                .build();
    }
}
