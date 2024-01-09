package com.example.demo.repositories;

import com.example.demo.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketRepositoriy extends JpaRepository<Bucket, Long> {

    Optional<Bucket> findByName(String name);

    void deleteBucketByName(String name);
}
