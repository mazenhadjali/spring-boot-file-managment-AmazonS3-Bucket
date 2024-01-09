package com.example.demo.dtos;

import com.example.demo.entity.Bucket;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileAttachmentDto {
    private Long id;
    private String fileName;
    private Long fileSize;
    private String contentType;
    private Bucket bucket;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
