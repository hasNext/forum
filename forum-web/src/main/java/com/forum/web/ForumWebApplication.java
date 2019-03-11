package com.forum.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.forum")
public class ForumWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumWebApplication.class, args);
    }

}
