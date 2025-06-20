package com.kaiqueapol.gridlab.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaiqueapol.gridlab.entities.User;
import com.kaiqueapol.gridlab.entities.dto.CredentialsDto;
import com.kaiqueapol.gridlab.infra.exceptions.AlreadyExistingUserException;
import com.kaiqueapol.gridlab.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepo;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByEmailIgnoreCase(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found. Try again!"));
	}

	public User login(CredentialsDto credDto) {
		User user = userRepo.findByEmailIgnoreCase(credDto.email())
				.orElseThrow(() -> new UsernameNotFoundException("User not found. Try again!"));

		if (encoder.encode(credDto.password()).equalsIgnoreCase(user.getPassword())) {
			return user;
		}

		throw new UsernameNotFoundException("User not found. Try again!");

	}

	public User register(CredentialsDto credDto) {
		Optional<User> existingUser = userRepo.findByEmailIgnoreCase(credDto.email());

		if (existingUser.isPresent()) {
			throw new AlreadyExistingUserException();
		}

		return userRepo.save(new User(credDto.email(), encoder.encode(credDto.password())));

	}
}
