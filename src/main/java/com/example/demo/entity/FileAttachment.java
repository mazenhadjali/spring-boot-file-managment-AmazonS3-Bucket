package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "FileAttachment", uniqueConstraints = {@UniqueConstraint(columnNames = {"objectKey"})})
public class FileAttachment {

    public FileAttachment(String fileName, String objectKey, Long fileSize, String contentType, Bucket bucket) {
        this.fileName = fileName;
        this.objectKey = objectKey;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.bucket = bucket;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String objectKey;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String contentType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Bucket bucket;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;
    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;

}
