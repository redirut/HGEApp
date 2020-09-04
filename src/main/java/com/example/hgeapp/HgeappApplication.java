package com.example.hgeapp;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class HgeappApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(HgeappApplication.class)
                .web(WebApplicationType.SERVLET);
        applicationBuilder.build()
                .addListeners(new ApplicationPidFileWriter("./pid.info"));
        applicationBuilder.run(args);
    }
}
