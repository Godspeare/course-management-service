package com.aptech.courseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseServiceApplication.mainLauncher(args));
    }

    private static Class<CourseServiceApplication> mainLauncher(String[] args) {
        return CourseServiceApplication.class;
    }
}