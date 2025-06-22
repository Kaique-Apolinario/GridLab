package com.kaiqueapol.gridlab.controllers;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaiqueapol.gridlab.entities.UserEntity;
import com.kaiqueapol.gridlab.entities.dto.CredentialsDTO;
import com.kaiqueapol.gridlab.services.TokenService;
import com.kaiqueapol.gridlab.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final TokenService tokenService;

	private final AuthenticationManager manager;

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody CredentialsDTO credDto) {

		// UsernamePasswordAuthenticationToken represents a login attempt, with username
		// and password
		// If successful, returns a Authenticated user
		Authentication authenticatedUser = manager
				.authenticate(new UsernamePasswordAuthenticationToken(credDto.email(), credDto.password()));

		// Now. we're going to use the user's email to generate a token in a JSON format
		Map<String, String> tokenJWT = tokenService.generateToken((UserEntity) authenticatedUser.getPrincipal());
		return ResponseEntity.ok(tokenJWT);
	}

	@PostMapping("/register")
	public ResponseEntity<UserEntity> register(@RequestBody CredentialsDTO signUpDto) {
		UserEntity userEntity = userService.register(signUpDto);
		return ResponseEntity.created(URI.create("/users/" + userEntity.getId())).body(userEntity);
	}
}
