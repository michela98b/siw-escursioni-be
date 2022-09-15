package it.uniroma3.siw.escursioni.authentication;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import it.uniroma3.siw.escursioni.model.Credentials;

@Component
public class JwtTokenUtil {

	private String secret = "secretKeyTest";
	private int jwtExpirationInMs = 86400000;

	// generate token for user
	public String generateAccessToken(Credentials userCredentials) {
		Map<String, Object> claims = new HashMap<>();
		String role = userCredentials.getRole();
		if (role.contains("ADMIN")) {
			claims.put("isAdmin", true);
		}
		if (role.contains("DEFAULT")) {
			claims.put("isUser", true);
		}
		String username = userCredentials.getUsername();
		return doGenerateToken(claims, username);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean validate(String authToken) {
		try {
			// Jwt token has not been tampered with
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		} catch (ExpiredJwtException ex) {
			throw new ExpiredJwtException(null, null, "Token has Expired", ex);
		}
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public SimpleGrantedAuthority getRoleFromToken(String authToken) {
		SimpleGrantedAuthority role = null;
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
		Boolean isAdmin = claims.get("isAdmin", Boolean.class);
		Boolean isUser = claims.get("isUser", Boolean.class);
		if (isAdmin != null && isAdmin == true) {
			role = new SimpleGrantedAuthority("ADMIN");
		}
		if (isUser != null && isUser == true) {
			role = new SimpleGrantedAuthority("DEFAULT");
		}
		return role;
	}

}
