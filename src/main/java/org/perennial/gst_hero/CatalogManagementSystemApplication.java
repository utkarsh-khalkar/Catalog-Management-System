package org.perennial.gst_hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


//@EnableScheduling
@SpringBootApplication(scanBasePackages = "org.perennial.gst_hero")
public class CatalogManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogManagementSystemApplication.class, args);
	}

}
