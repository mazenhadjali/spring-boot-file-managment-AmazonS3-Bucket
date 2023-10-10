package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

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
