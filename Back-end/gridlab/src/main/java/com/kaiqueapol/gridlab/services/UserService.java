package com.kaiqueapol.gridlab.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaiqueapol.gridlab.entities.UserEntity;
import com.kaiqueapol.gridlab.entities.dto.CredentialsDTO;
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

	public UserEntity register(CredentialsDTO credDto) {
		Optional<UserEntity> existingUser = userRepo.findByEmailIgnoreCase(credDto.email());

		if (existingUser.isPresent()) {
			throw new AlreadyExistingUserException();
		}

		return userRepo.save(new UserEntity(credDto.email(), encoder.encode(credDto.password())));

	}
}
