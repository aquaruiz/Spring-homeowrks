package com.mm.homeworks.authentication;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mm.homeworks.model.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@ConfigurationProperties(prefix = "app.jwt")
@Component
public class JwtTokenProvider {
	@Value("${app.jwt.jwtSecret}")
	private String jwtSecret;
	@Value("${app.jwt.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	public String generateToken(Authentication authentication) {
		User userPrincipal = (User) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		// @formatter:on

		return Jwts.builder()
					.setSubject(userPrincipal.getUsername())
					.setIssuedAt(new Date())
					.setExpiration(expiryDate)
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
		
		// @formatter:off
	}

	public boolean validateToken(String authToken, UserDetails userDetails) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			final String username = extractUsername(authToken);
			if (!username.equals(userDetails.getUsername())) {
				// TODO log in logger "Users DO not match"
				return false;
			}
			return true;
		} catch (SignatureException ex) {
			// TODO log in logger "Invalid JWT signature"
			return false;
		} catch (MalformedJwtException ex) {
			// TODO log in logger "Invalid JWT token"
			return false;
		} catch (ExpiredJwtException ex) {
			// TODO log in logger "Expired JWT token"
			return false;
		} catch (UnsupportedJwtException ex) {
			// TODO log in logger "Unsupported JWT token"
			return false;
		} catch (IllegalArgumentException ex) {
			// TODO log in logger "JWT claims string is empty."
			return false;
		}
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}
}
