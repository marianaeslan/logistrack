package br.com.logistrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LogistrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogistrackApplication.class, args);
	}

}
