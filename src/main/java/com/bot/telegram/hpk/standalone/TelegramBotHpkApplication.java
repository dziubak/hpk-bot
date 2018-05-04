package com.bot.telegram.hpk.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bot.telegram.hpk")
public class TelegramBotHpkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotHpkApplication.class, args);
	}
}
