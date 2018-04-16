package ca.codeless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Run {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Run.class);
    }
}
