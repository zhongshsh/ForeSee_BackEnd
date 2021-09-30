package com.ForeSee.ForeSee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
=======
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
<<<<<<< HEAD
=======
@EnableEurekaClient
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
@EnableHystrixDashboard
public class ClientMain {
    public static void main(String[] args) {
        SpringApplication.run(ClientMain.class, args);
    }
}
