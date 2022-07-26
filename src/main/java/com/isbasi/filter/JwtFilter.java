package com.isbasi.filter;

import java.net.InetSocketAddress;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.isbasi.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtFilter implements GatewayFilter {

	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		String localAddress = request.getLocalAddress().getAddress().toString();

		log.info("localAddress: {}", localAddress);

		String remoteAddress = request.getRemoteAddress().getHostName();

		log.info("remoteAddress: {}", remoteAddress);

		HttpHeaders headers = request.getHeaders();

		boolean hasAuth = headers.containsKey(AUTHORIZATION);

		if (!hasAuth) {

			log.info("Headers has not Authorization");
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();

		}

		List<String> headerList = headers.get(AUTHORIZATION);

		String token = headerList.get(0);

		if (token.isBlank()) {
			log.info("Headers has Authorization but has not token");
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}

		if (!jwtUtil.isValidToken(token)) {
			log.info("Token is not valid");
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}

		return chain.filter(exchange);
	}

}
