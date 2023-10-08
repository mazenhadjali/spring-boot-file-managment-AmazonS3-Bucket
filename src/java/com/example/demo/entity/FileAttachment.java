package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FileAttachment", uniqueConstraints = {@UniqueConstraint(columnNames = {"bucketName", "fileName", "objectName"})})
public class FileAttachment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String bucketName;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private Long fileSize;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private String objectName;

}
