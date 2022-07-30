package com.isbasi.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "logo_yazilim-patika-top-secret-key-logo_yazilim-patika-top-secret-key";

	public Claims getClaims(String token) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		

		//// @formatter:off
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

		// @formatter:on
		
		

	}

	public boolean isValidToken(String token) {

		Integer expirationDate = (Integer) getClaims(token).get(Claims.EXPIRATION);

		return new Date(expirationDate).before(new Date());

	}

}
