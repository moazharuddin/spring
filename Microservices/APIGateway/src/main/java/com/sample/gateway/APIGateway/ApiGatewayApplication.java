package com.sample.gateway.APIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableZuulProxy
public class VegaApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(VegaApiGatewayApplication.class, args);
	}


	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()

				.route(r -> r.path("/portal/**")
						//.filters(f -> f.stripPrefix(1))
						.uri("lb://portal"))

				.route(r -> r.path("/process/**")
						.uri("lb://pre-processing"))

				.build();
	}

}
