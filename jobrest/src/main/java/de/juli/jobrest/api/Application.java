package de.juli.jobrest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class Application {

    // start everything
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}