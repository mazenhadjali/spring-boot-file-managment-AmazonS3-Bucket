package com.example.demo.dtos;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDto {
    private Long id;
    private String name;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
