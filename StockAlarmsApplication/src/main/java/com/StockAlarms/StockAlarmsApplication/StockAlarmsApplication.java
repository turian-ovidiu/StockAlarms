package com.StockAlarms.StockAlarmsApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockAlarmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockAlarmsApplication.class, args);
	}
}
