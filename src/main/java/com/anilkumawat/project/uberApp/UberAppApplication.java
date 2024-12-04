package com.anilkumawat.project.uberApp;

import com.anilkumawat.project.uberApp.dto.SignUpDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UberAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberAppApplication.class, args);
		SignUpDto signupDto = new SignUpDto("","","a");
		System.out.println(signupDto.getPassword());

	}

}
