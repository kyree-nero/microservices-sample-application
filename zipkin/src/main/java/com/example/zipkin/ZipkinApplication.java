package com.example.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@zipkin.server.EnableZipkinServer
public class ZipkinApplication {
	public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
