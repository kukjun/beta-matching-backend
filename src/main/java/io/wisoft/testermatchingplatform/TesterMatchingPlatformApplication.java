package io.wisoft.testermatchingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@EnableScheduling
@SpringBootApplication
public class TesterMatchingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesterMatchingPlatformApplication.class, args);
    }

}
