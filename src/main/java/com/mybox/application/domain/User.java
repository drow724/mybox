package com.mybox.application.domain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybox.application.domain.constant.Rank;
import com.mybox.application.domain.constant.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Getter
    private String id;
    
    private String username;

    private String password;

    @Getter @Setter
    private Boolean enabled;

    @Getter
    private Rank rank = Rank.NORMAL;
    
    @Getter
    private Long current = 0L;
    
    @Getter @Setter
    private List<Role> roles;
    
    @Getter
    private Token token;
    
    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void token(String accessToken, String refreshToken) {
		this.token = new Token(accessToken, refreshToken);
	}

	public User(String username, String password, Boolean enabled, List<Role> roles) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	public User(String id, String username, String password, Boolean enabled, List<Role> roles, Rank rank,
			Long current) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
		this.rank = rank;
		this.current = current;
	}

}