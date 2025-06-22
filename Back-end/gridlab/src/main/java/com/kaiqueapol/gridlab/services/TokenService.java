package com.kaiqueapol.gridlab.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.kaiqueapol.gridlab.entities.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenService {

	// This is going to be the signature of every token generated
	private Key SECRET_KEY = Keys
			.hmacShaKeyFor("testeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee".getBytes(StandardCharsets.UTF_8));

	public Map<String, String> generateToken(UserEntity user) {

		String tokenString = Jwts.builder().subject(user.getEmail()) // Set the subject (usually the username/email)
				.issuedAt(new Date(System.currentTimeMillis())) // Token creation time
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Expiration (in ms, so 30m here)
				.signWith(SECRET_KEY) // use Key, not String
				.compact(); // Generate the final JWT string
		Map<String, String> tokenAsJson = new HashMap<>();
		tokenAsJson.put("token", tokenString);
		return tokenAsJson;
	}

	public String returnUserFromToken(String token) {
		return Jwts.parser() // Starts building a JWT parser
				.verifyWith((SecretKey) SECRET_KEY) // Tells that the key is in this variable
				.build() // Finishes building
				.parseSignedClaims(token) // Parses and verifies the signed JWT
				.getPayload(). // Gets the claims (infos like subject, issued at, expiration etc.)
				getSubject(); // Returns the username/email
	}
}
