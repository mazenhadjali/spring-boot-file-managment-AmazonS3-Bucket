package com.example.demo.configurations;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class S3ClientConfigurer {

    @Value("${minio.minioEndpoint}")
    private String minioEndpoint;

    @Value("${minio.minioAccessKey}")
    private String minioAccessKey;

    @Value("${minio.minioSecretKey}")
    private String minioSecretKey;

    public AWSCredentials credentials() {
        return new BasicAWSCredentials(
                Objects.requireNonNull(minioAccessKey),
                Objects.requireNonNull(minioSecretKey)
        );
    }

    @Bean
    public AmazonS3 amazonS3() {
        ClientConfiguration clientConfiguration = new ClientConfiguration()
                .withMaxErrorRetry(3)
                .withMaxConnections(350);

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(minioEndpoint, null))
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withClientConfiguration(clientConfiguration)
                .build();
    }
}
