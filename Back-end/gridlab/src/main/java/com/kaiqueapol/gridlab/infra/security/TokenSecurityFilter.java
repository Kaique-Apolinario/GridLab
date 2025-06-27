package com.kaiqueapol.gridlab.infra.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kaiqueapol.gridlab.entities.UserEntity;
import com.kaiqueapol.gridlab.infra.exceptions.UserNotFoundException;
import com.kaiqueapol.gridlab.repositories.UserRepository;
import com.kaiqueapol.gridlab.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenSecurityFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final UserRepository userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = getTokenFromReq(request); // For each request, it'll take the request's token

		if (token != null) {
			// If there is a token, it'll be used to retrieve its owner (user)
			String email = tokenService.returnUserFromToken(token);
			// Then, we'll verify if the owner is in our database
			Optional<UserEntity> user = userRepo.findByEmailIgnoreCase(email);
			if (user.isEmpty())
				throw new UserNotFoundException();

			// If it's all alright, the user gets authenticated with its password (already
			// validated, so it's null here) and its roles
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.get(), null,
					user.get().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			// Stores the Authentication object into the current security context, making SS
			// treat the request as authenticated
		}

		filterChain.doFilter(request, response);
	}

	// If the request has a token in its header, it is going to be returned, else,
	// nothing.
	private String getTokenFromReq(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}

}
