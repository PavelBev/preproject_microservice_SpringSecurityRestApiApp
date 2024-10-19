package ru.pavelbev.springSecurityAppByPavelBev;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityAppByPavelBevApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAppByPavelBevApplication.class, args);
	}
}


//public class BCryptExample {
//	public static void main(String[] args) {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String originalPassword = "paveltest";
//		String hashedPassword = passwordEncoder.encode(originalPassword);
//
//		System.out.println("Original password: " + originalPassword);
//		System.out.println("Hashed password: " + hashedPassword);
//	}
//}