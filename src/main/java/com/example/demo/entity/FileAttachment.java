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
@Table(name = "FileAttachment", uniqueConstraints = {@UniqueConstraint(columnNames = {"bucketName", "fileName", "objectName"})})
public class FileAttachment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private Long fileSize;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private String objectName;

    @ManyToOne( cascade = CascadeType.ALL )
    private Bucket bucket;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;
    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;

}
