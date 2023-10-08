package com.example.demo.repositories;

import com.example.demo.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment,Long> {
    List<FileAttachment> getAllByBucketName(String bucketName);
}
