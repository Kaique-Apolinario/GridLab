package com.kaiqueapol.gridlab.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaiqueapol.gridlab.entities.UserEntity;
import com.kaiqueapol.gridlab.entities.dto.LoginCredentialsDto;
import com.kaiqueapol.gridlab.entities.dto.RegisterCredentialsDto;
import com.kaiqueapol.gridlab.entities.dto.UserTokenDto;
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
	public ResponseEntity<UserTokenDto> login(@RequestBody LoginCredentialsDto credDto) {

		// UsernamePasswordAuthenticationToken represents a login attempt, with username
		// and password
		// If successful, returns a Authenticated user
		Authentication authenticatedUser = manager
				.authenticate(new UsernamePasswordAuthenticationToken(credDto.email(), credDto.password()));

		// Now. we're going to use the user's email to generate a token in a JSON format

		UserTokenDto tokenAndUserId = tokenService.generateToken((UserEntity) authenticatedUser.getPrincipal());

		return ResponseEntity.ok().body(tokenAndUserId);
	}

	@PostMapping("/register")
	public ResponseEntity<UserEntity> register(@RequestBody RegisterCredentialsDto signUpDto) {
		UserEntity userEntity = userService.register(signUpDto);
		return ResponseEntity.created(URI.create("/users/" + userEntity.getId())).body(userEntity);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		return ResponseEntity.ok("Logout realizado com sucesso!");
	}
}
