package com.kaiqueapol.gridlab.infra.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kaiqueapol.gridlab.entities.UserEntity;
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

		String token = getTokenFromReq(request);
		System.out.println(token + " testeeeeeeeee");

		if (token != null) {
			String email = tokenService.returnUserFromToken(token);
			System.out.println(email + " AQUIIIIIIII");
			UserEntity user = userRepo.findByEmailIgnoreCase(email).orElseThrow();

			Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
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
