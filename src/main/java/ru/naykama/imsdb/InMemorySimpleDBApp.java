package ru.naykama.imsdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ru.naykama.*")

public class InMemorySimpleDBApp {
    public static void main(String[] args) {
        SpringApplication.run(InMemorySimpleDBApp.class, args);
    }
}
