package com.example.hgeapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class HgeappApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(HgeappApplication.class);
        applicationBuilder.build().addListeners(new ApplicationPidFileWriter("application.pid"));
        applicationBuilder.run(args);
    }
}
