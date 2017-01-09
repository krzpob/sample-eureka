package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@EnableEurekaClient
@SpringBootApplication
public class DemoApplication{


	@LoadBalanced
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}



	public static void main(String[] args) {
		new SpringApplicationBuilder(DemoApplication.class).web(false).run(args);

	}



}

@Component
class RestDemo implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger(RestDemo.class);


	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void run(String... strings) throws Exception {
		LOGGER.info("Run demo apps");
		ResponseEntity<String> responce = restTemplate.exchange("http://demo-service/hello", HttpMethod.GET,null,String.class, String.class, "demo-service");

		LOGGER.info("Responce: {}",responce);
		if(responce.hasBody()){
			LOGGER.info("Fetch instance at "+responce.getBody());
		} else {
			LOGGER.warn("No body");
		}
	}
}