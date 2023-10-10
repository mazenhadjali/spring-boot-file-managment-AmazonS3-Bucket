package com.example.demo.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class CommonUtils {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
