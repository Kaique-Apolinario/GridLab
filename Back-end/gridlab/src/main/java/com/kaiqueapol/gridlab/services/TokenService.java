package com.kaiqueapol.gridlab.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

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

	public String generateToken(UserEntity user) {

		return Jwts.builder().subject(user.getEmail()) // <-- Set the subject (usually the username/email)
				.issuedAt(new Date(System.currentTimeMillis())) // <-- Token creation time
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30)) // <-- Expiration (in ms)
				.signWith(SECRET_KEY) // <- use Key, not String
				.compact(); // <-- Generate the final JWT string
	}

	public String returnUserFromToken(String token) {
		System.out.println(Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build().parseSignedClaims(token)
				.getPayload().getSubject() + " VERIFICAR SE AQUI TA FECHO");
		return Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build().parseSignedClaims(token).getPayload()
				.getSubject();

	}
}
