package ua.training.CruiseLineSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CruiseLineSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruiseLineSpringApplication.class, args);
	}

}
