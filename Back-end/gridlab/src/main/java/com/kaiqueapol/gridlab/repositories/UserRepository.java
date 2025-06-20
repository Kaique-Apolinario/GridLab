package com.kaiqueapol.gridlab.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaiqueapol.gridlab.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmailIgnoreCase(String email);

}
