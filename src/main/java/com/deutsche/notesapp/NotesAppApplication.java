package com.deutsche.notesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class NotesAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesAppApplication.class, args);
    }

}
