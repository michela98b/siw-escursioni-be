package it.uniroma3.siw.escursioni.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import it.uniroma3.siw.escursioni.model.Credentials;
import it.uniroma3.siw.escursioni.service.CredentialsService;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{

	private JwtTokenUtil jwtTokenUtil;

	private final CredentialsService credentialsService;

	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
			CredentialsService CredentialsService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.credentialsService = CredentialsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain)
					throws ServletException, IOException {
		// Get authorization header and validate
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		// Get jwt token and validate
		final String token = header.split(" ")[1].trim();
		if (!jwtTokenUtil.validate(token)) {
			chain.doFilter(request, response);
			return;
		}

		// Get user identity and set it on the spring security context
		Credentials credentials = credentialsService
				.getCredentials(jwtTokenUtil.getUsername(token));

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(credentials.getRole()));
		
		UsernamePasswordAuthenticationToken
		authentication = new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), null, credentials == null ? List.of() : authorities 
				);

		authentication.setDetails(
				new WebAuthenticationDetailsSource().buildDetails(request)
				);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
}
