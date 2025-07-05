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
import com.kaiqueapol.gridlab.entities.dto.LoginCredentialsDto;
import com.kaiqueapol.gridlab.entities.dto.RegisterCredentialsDto;
import com.kaiqueapol.gridlab.entities.dto.UserTokenDto;
import com.kaiqueapol.gridlab.services.TokenService;
import com.kaiqueapol.gridlab.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "GridLabAPI user methods")
public class UserController {

	private final UserService userService;
	private final TokenService tokenService;

	private final AuthenticationManager manager;

	@PostMapping("/login")
	@Operation(summary = "It authenticates the user into the API", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<UserTokenDto> login(@RequestBody LoginCredentialsDto credDto, HttpServletResponse response) {
		// UsernamePasswordAuthenticationToken represents a login attempt, with username
		// and password
		// If successful, returns a Authenticated user
		Authentication authenticatedUser = manager
				.authenticate(new UsernamePasswordAuthenticationToken(credDto.email(), credDto.password()));

		// Now. we're going to use the user's email to generate a token in a JSON format

		UserTokenDto tokensAndUserId = tokenService.generateTokens((UserEntity) authenticatedUser.getPrincipal(),
				response);
		return ResponseEntity.ok().body(tokensAndUserId);
	}

	@PostMapping("/refreshToken")
	@Operation(summary = "It produces a new access token for the user", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<UserTokenDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.ok().body(tokenService.refreshAccessTokenFromCookie(request, response));
	}

	@PostMapping("/register")
	@Operation(summary = "It registers a new user into the API", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<String> register(@RequestBody RegisterCredentialsDto signUpDto) {
		UserEntity userEntity = userService.register(signUpDto);
		return ResponseEntity.created(URI.create("/users/" + userEntity.getId())).body("User registered successfully!");
	}

	@PostMapping("/logout")
	@Operation(summary = "It logs out the user from the API", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<String> logout(@RequestBody Map<String, String> refreshTokenFromHeader) {
		tokenService.invalidateToken(refreshTokenFromHeader.get("refreshToken"));
		return ResponseEntity.ok().body("You have successfully logged out! See you soon!");
	}
}
