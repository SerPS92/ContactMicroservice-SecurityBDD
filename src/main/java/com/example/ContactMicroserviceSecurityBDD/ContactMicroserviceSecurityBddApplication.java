package com.example.ContactMicroserviceSecurityBDD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class ContactMicroserviceSecurityBddApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactMicroserviceSecurityBddApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}

}
