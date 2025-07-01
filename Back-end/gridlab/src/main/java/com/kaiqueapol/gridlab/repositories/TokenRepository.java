package com.kaiqueapol.gridlab.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kaiqueapol.gridlab.entities.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

	@Query("SELECT token FROM TokenEntity token WHERE token.token = :token")
	public TokenEntity findTokenEntityByToken(String token);

	@Query("SELECT token FROM TokenEntity token WHERE token.tokenUser.email = :email")
	public Optional<TokenEntity> findTokenEntityByEmail(String email);

	@Modifying // Required if I'm going to use a custom delete query
	@Query("DELETE FROM TokenEntity t WHERE t.token = :token")
	public void deleteTokenEntity(String token);

}
