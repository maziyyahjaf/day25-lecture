package vttp5.paf.day25.day_paf_25_pm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp5.paf.day25.day_paf_25_pm.service.MessagePoller;

@SpringBootApplication
public class DayPaf25PmApplication implements CommandLineRunner {

	// slide 11

	@Autowired
	MessagePoller messagePoller;

	public static void main(String[] args) {
		SpringApplication.run(DayPaf25PmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		messagePoller.start();
	}

}
