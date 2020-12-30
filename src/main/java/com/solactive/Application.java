package com.solactive;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SuppressWarnings("javadoc")
@SpringBootApplication
@ComponentScan(basePackages = "com.solactive")
public class Application {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new SpringApplicationBuilder().main(Application.class).sources(Application.class).profiles("server").run(args);
	}

	@Bean
	ExecutorMultiRequests executorMultiRequests() {
		return new ExecutorMultiRequests();
	}

}
