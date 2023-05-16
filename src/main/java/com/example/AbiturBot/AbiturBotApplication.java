package com.example.AbiturBot;

import com.example.AbiturBot.model.University;
import com.example.AbiturBot.service.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication()
public class AbiturBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbiturBotApplication.class, args);
	}

}
