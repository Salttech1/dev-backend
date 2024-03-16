package kraheja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableFeignClients
@SpringBootApplication
public class KrahejaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrahejaApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	public void onStart() {
		log.info("Application started successfully.");
	}
}
