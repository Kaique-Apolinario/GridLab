package com.kaiqueapol.gridlab.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kaiqueapol.gridlab.entities.TokenEntity;
import com.kaiqueapol.gridlab.entities.UserEntity;
import com.kaiqueapol.gridlab.entities.dto.UserTokenDto;
import com.kaiqueapol.gridlab.infra.exceptions.InvalidTokenException;
import com.kaiqueapol.gridlab.repositories.TokenRepository;
import com.kaiqueapol.gridlab.repositories.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenService {
	private final UserRepository userRepo;
	private final TokenRepository tokenRepo;

	// This is going to be the signature of every token generated
	@Value("${jwt.secret}")
	private String keyString;

	private Key SECRET_KEY;

	// Used to be automatically executed after dependencies injection and
	// class/bean creation
	@PostConstruct
	public void init() {
		SECRET_KEY = Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));
	}

	@Transactional
	public UserTokenDto generateTokens(UserEntity user, HttpServletResponse response) {

		UserEntity foundUser = userRepo.findByEmailIgnoreCase(user.getEmail()).get();

		String accessToken = Jwts.builder().subject(user.getEmail()) // Set the subject (usually the username/email)
				.issuedAt(new Date(System.currentTimeMillis())) // Token creation time
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // Expiration (in ms, so 15m here)
				.signWith(SECRET_KEY) // use Key, not String
				.compact(); // Generate the final JWT string

		String refreshToken = Jwts.builder().subject(user.getEmail()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
				.signWith(SECRET_KEY).compact();

		// It will replace the old refreshToken by a new one in the database
		if (tokenRepo.findTokenEntityByEmail(user.getEmail()).isPresent()) {
			Optional<TokenEntity> tokensOfUser = tokenRepo.findTokenEntityByEmail(user.getEmail());
			tokenRepo.deleteTokenEntity(tokensOfUser.get().getToken());
		}
		TokenEntity tokenEntity = new TokenEntity(refreshToken, foundUser, true);
		tokenRepo.save(tokenEntity);

		// return refresh token as a HttpOnly cookie to the frontend, which is safer,
		// since it isn't accessible using JS
		response.addCookie(refreshTokenCookie(tokenEntity, response));

		foundUser.setToken(tokenEntity);
		userRepo.save(foundUser);

		return new UserTokenDto(accessToken, user.getId());
	}

	@Transactional
	public String returnUserFromToken(String token, HttpServletResponse response) {
		String userFromToken = null;
		try {
			userFromToken = Jwts.parser() // Starts building a JWT parser
					.verifyWith((SecretKey) SECRET_KEY) // Tells that the key is in this variable
					.build() // Finishes building
					.parseSignedClaims(token) // Parses and verifies the signed JWT
					.getPayload(). // Gets the claims (infos like subject, issued at, expiration etc.)
					getSubject(); // Returns the username/email
		} catch (ExpiredJwtException e) {
			refreshAccessToken(token, response);
		}
		return userFromToken;
	}

	@Transactional
	public UserTokenDto refreshAccessToken(String refreshToken, HttpServletResponse response) {
		TokenEntity tokenEntity = Optional.ofNullable(tokenRepo.findTokenEntityByToken(refreshToken))
				.orElseThrow(() -> new InvalidTokenException());
		tokenRepo.deleteTokenEntity(tokenEntity.getToken());

		return generateTokens(tokenEntity.getTokenUser(), response);
	}

	public UserTokenDto refreshAccessTokenFromCookie(HttpServletRequest request, HttpServletResponse response) {
		String refreshTokenFromCookie = null;

		// Get cookies from the request, then looks for our refresh token and take its
		// value
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refreshToken".equals(cookie.getName())) {
					refreshTokenFromCookie = cookie.getValue();
					break;
				}
			}
		}

		if (refreshTokenFromCookie == null) {
			throw new InvalidTokenException();
		}

		return refreshAccessToken(refreshTokenFromCookie, response);
	}

	@Transactional
	public void invalidateToken(String refreshToken) {
		TokenEntity tokenEntity = Optional.ofNullable(tokenRepo.findTokenEntityByToken(refreshToken))
				.orElseThrow(() -> new InvalidTokenException());
		tokenRepo.delete(tokenEntity);
	}

	public Cookie refreshTokenCookie(TokenEntity refreshToken, HttpServletResponse response) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.getToken());
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false); // HTTPS only, but mine is http
		refreshTokenCookie.setPath("/");
		Long expirationTime = Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build()
				.parseSignedClaims(refreshToken.getToken()).getPayload().getExpiration().getTime();
		refreshTokenCookie.setMaxAge((int) (expirationTime - (System.currentTimeMillis() / 1000)));

		return refreshTokenCookie;

	}
}
