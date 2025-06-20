package com.kaiqueapol.gridlab.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaiqueapol.gridlab.entities.User;
import com.kaiqueapol.gridlab.entities.dto.CredentialsDto;
import com.kaiqueapol.gridlab.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody CredentialsDto credDto) {
		System.out.println(credDto);
		User user = userService.login(credDto);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody CredentialsDto signUpDto) {
		User user = userService.register(signUpDto);
		return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
	}
}
