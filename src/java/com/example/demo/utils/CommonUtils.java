package com.example.demo.utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class CommonUtils {

//    public static File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
//        File file = new File(multipartFile.getOriginalFilename());
//        try (FileOutputStream outputStream = new FileOutputStream(file)) {
//            FileCopyUtils.copy(multipartFile.getInputStream(), outputStream);
//        }
//        return file;
//    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
