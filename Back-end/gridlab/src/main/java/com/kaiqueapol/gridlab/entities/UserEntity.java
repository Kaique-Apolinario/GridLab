package com.kaiqueapol.gridlab.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "T_RC_USERS")
public class UserEntity implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String password;

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FileEntity> fileList;

	public UserEntity(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	@Override // Deals with permissions
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

}
