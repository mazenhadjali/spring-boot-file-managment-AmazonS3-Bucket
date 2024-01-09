package com.example.demo.services.impl;

import com.example.demo.dtos.FileAttachmentDto;
import com.example.demo.entity.FileAttachment;
import com.example.demo.repositories.FileAttachmentRepository;
import com.example.demo.services.FileAttachmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileAttachmentServiceImp implements FileAttachmentService {

    private final FileAttachmentRepository fileAttachmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public FileAttachment saveFileAttachment(FileAttachment fileAttachment) {
        return fileAttachmentRepository.save(fileAttachment);
    }

    @Override
    public List<FileAttachmentDto> getAllFileAttachments(String bucketName) {
        return fileAttachmentRepository.getAllByBucketName(bucketName).stream().map(fileAttachment -> modelMapper.map(fileAttachment, FileAttachmentDto.class)).collect(Collectors.toList());
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
