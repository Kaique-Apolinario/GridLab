package com.kaiqueapol.gridlab.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaiqueapol.gridlab.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public Optional<UserEntity> findByEmailIgnoreCase(String email);

}
