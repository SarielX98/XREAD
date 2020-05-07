package com.sarielx.study.xr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class XrApplication {

    public static void main(String[] args) {
        SpringApplication.run(XrApplication.class, args);
    }

}
