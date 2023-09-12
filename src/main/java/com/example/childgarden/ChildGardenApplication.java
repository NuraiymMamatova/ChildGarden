package com.example.childgarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class ChildGardenApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ChildGardenApplication.class, args);
        System.out.println("Hello here is your token " + generateSafeToken());
        System.out.println("CurrentDir: " + (new File(".").getCanonicalPath()));
    }

    private static String generateSafeToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36]; // 36 bytes * 8 = 288 bits, a little bit more than
        // the 256 required bits
        random.nextBytes(bytes);
        var encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }

}
