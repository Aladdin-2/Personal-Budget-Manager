package com.aladdin.personalbudgetcontrollerdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableScheduling
@CrossOrigin(origins = "http://localhost:3000")
public class PersonalBudgetControllerDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBudgetControllerDemo2Application.class, args);
    }

}
