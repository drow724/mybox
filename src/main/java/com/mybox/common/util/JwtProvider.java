package com.mybox.common.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.mybox.application.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtProvider {

	@Value("${springbootwebfluxjjwt.jjwt.secret}")
	private String secret;

	@Value("${springbootwebfluxjjwt.jjwt.expiration}")
	private String expirationTime;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	@SuppressWarnings("unchecked")
	public List<GrantedAuthority> getRoles(String token) {
		List<String> roles = getAllClaimsFromToken(token).get("role", List.class);
		if (roles != null) {
			return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}
		return null;
	}

	public User getUser(String token) {
		User user = getAllClaimsFromToken(token).get("user", User.class);
		return user;
	}

	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRoles());
		Long expirationTimeLong = Long.parseLong(expirationTime); // in second
		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
		return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(key).compact();
	}

	public String generateRefreshToken(User user) {
		Long expirationTimeLong = Long.parseLong(expirationTime); // in second

		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

		return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(key).compact();
	}

	public Boolean validateToken(String token) {
		Boolean expiration = Boolean.FALSE;
		try {
			expiration = !getAllClaimsFromToken(token).getExpiration().before(new Date());
		} catch(ExpiredJwtException e) {
			return false;
		}
		return expiration;
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}