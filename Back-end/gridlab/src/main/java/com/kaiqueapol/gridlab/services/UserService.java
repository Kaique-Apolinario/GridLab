package com.kaiqueapol.gridlab.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaiqueapol.gridlab.entities.UserEntity;
import com.kaiqueapol.gridlab.entities.dto.RegisterCredentialsDto;
import com.kaiqueapol.gridlab.infra.exceptions.AlreadyExistingUserException;
import com.kaiqueapol.gridlab.infra.exceptions.NotMatchingPasswords;
import com.kaiqueapol.gridlab.infra.exceptions.UserNotFoundException;
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
}
