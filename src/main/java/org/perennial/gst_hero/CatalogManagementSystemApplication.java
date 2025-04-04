package org.perennial.gst_hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@SpringBootApplication(scanBasePackages = "org.perennial.gst_hero")
@EnableScheduling
public class CatalogManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogManagementSystemApplication.class, args);
	}

}
