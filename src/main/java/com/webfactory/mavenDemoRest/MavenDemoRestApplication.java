package com.webfactory.mavenDemoRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//import com.example.mavenDemoRest.services.PostService;

@SpringBootApplication
public class MavenDemoRestApplication {






	public static void main(String[] args) {





		//loadData();
		//startApp();


		SpringApplication.run(MavenDemoRestApplication.class, args);
	}

}
