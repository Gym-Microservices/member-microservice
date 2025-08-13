package com.gym.member_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MemberMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberMicroserviceApplication.class, args);
	}

}
