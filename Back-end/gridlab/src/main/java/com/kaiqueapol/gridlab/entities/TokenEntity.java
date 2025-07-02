package com.kaiqueapol.gridlab.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_tokens")
@ToString(exclude = "tokenUser")
public class TokenEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_token")
	private Long id;

	private String token;

	@OneToOne()
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	@JsonIgnore
	private UserEntity tokenUser;

	private boolean valid;

	public TokenEntity(String token, UserEntity tokenUser, boolean valid) {
		this.token = token;
		this.tokenUser = tokenUser;
		this.valid = valid;
	}

}
