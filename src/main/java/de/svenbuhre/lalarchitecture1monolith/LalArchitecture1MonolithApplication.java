package de.svenbuhre.lalarchitecture1monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LalArchitecture1MonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(LalArchitecture1MonolithApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
