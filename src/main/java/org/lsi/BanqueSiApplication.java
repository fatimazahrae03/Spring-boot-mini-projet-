package org.lsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Add this annotation to indicate it's a Spring Boot application
public class BanqueSiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BanqueSiApplication.class, args); // Fixed class name in the run method
    }
}
