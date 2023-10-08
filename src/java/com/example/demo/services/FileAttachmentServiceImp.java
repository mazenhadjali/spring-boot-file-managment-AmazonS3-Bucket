package com.example.demo.services;

import com.example.demo.entity.FileAttachment;
import com.example.demo.repositories.FileAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileAttachmentServiceImp implements  FileAttachmentService {

    private final FileAttachmentRepository fileAttachmentRepository;

    @Override
    public FileAttachment saveFileAttachment(FileAttachment fileAttachment) {
        // You can add additional logic or validation here before saving if needed
        return fileAttachmentRepository.save(fileAttachment);
    }

    @Override
    public List<FileAttachment> getAllFileAttachments(String bucketName) {
        return fileAttachmentRepository.getAllByBucketName(bucketName);
    }

    @Override
    public Optional<FileAttachment> getFileAttachmentById(Long id) {
        return fileAttachmentRepository.findById(id);
    }

    @Override
    public void deleteFileAttachmentById(Long id) {
        fileAttachmentRepository.deleteById(id);
    }
}
