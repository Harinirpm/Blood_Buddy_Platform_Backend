package com.bbp.BBPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BbPlatformApplication {
	public static void main(String[] args) {
		SpringApplication.run(BbPlatformApplication.class, args);
		System.out.println("Blood buddy platform Server started...");
	}
}