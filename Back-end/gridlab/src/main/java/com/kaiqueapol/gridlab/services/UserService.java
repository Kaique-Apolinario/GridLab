package com.kaiqueapol.gridlab.services;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.kaiqueapol.gridlab.entities.UserEntity;
import com.kaiqueapol.gridlab.entities.dto.RegisterCredentialsDto;
import com.kaiqueapol.gridlab.infra.exceptions.AlreadyExistingUserException;
import com.kaiqueapol.gridlab.infra.exceptions.InvalidTokenException;
import com.kaiqueapol.gridlab.infra.exceptions.NotMatchingPasswords;
import com.kaiqueapol.gridlab.infra.exceptions.UserNotFoundException;
import com.kaiqueapol.gridlab.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepo;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByEmailIgnoreCase(username).orElseThrow(() -> new UserNotFoundException());
	}

	public UserEntity register(RegisterCredentialsDto credDto) {
		if (!credDto.password().equals(credDto.confirmationPassword())) {
			throw new NotMatchingPasswords();
		}

		Optional<UserEntity> existingUser = userRepo.findByEmailIgnoreCase(credDto.email());

		if (existingUser.isPresent()) {
			throw new AlreadyExistingUserException();
		}

		return userRepo.save(new UserEntity(credDto.email(), encoder.encode(credDto.password())));
	}

	@Transactional // Ensures that the logout process, including any database changes, is handled
					// within a single transaction
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		// Retrieves the current authentication information from the SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Checks if the user is authenticated (authentication is not null and
		// authenticated)
		if (authentication != null && authentication.isAuthenticated()) {

			// Logs out the user by clearing their authentication info from the
			// SecurityContext
			new SecurityContextLogoutHandler().logout(request, response, authentication);

			// Gets the Authorization header from the request to retrieve the JWT token
			String token = request.getHeader("Authorization");

			// Checks if the token is in "Bearer <token>" format, then removes "Bearer "
			// prefix to extract only the token value
			if (token != null && token.startsWith("Bearer ")) {
				token = token.substring(7); // Extracts the actual token by removing the "Bearer " prefix
			} else {
				// If no valid token is provided, throws a custom exception indicating the
				// refresh token does not exist
				throw new InvalidTokenException();
			}

		} else {
			// Throws an exception if the user was not authenticated in the first place
			throw new InvalidTokenException();
		}
	}
}
