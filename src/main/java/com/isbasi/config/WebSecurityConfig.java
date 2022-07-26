package com.isbasi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.isbasi.filter.JwtFilter;

@Configuration
public class WebSecurityConfig {

	@Autowired
	private JwtFilter gatewayFilter;

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

		http.httpBasic().disable().formLogin().disable().csrf().disable();

		return http.build();

	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {

		//// @formatter:off
 
		
		return builder.routes()
				.route("auth", r -> r.path("/auth/**")
						.uri("http://localhost:1907"))
				.route("usersss", route -> route.path("/users/**")
						.filters(filter -> filter.filter(gatewayFilter))
						.uri("http://localhost:8080"))
				.build();
		// @formatter:on

	}

}
