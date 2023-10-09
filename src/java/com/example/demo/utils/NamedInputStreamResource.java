package com.example.demo.utils;


import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

public class NamedInputStreamResource extends InputStreamResource {
    private String filename;

    public NamedInputStreamResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return filename;
    }
}
