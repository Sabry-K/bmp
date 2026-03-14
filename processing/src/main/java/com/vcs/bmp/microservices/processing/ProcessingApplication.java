package com.vcs.bmp.microservices.processing;

import com.vcs.bmp.microservices.order.configurations.SubscriptionConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        SubscriptionConfiguration.class
})
public class ProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessingApplication.class, args);
    }

}
