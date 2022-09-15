package it.uniroma3.siw.escursioni.authentication;

import static it.uniroma3.siw.escursioni.model.Credentials.ADMIN_ROLE;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource datasource;
	
	@Autowired
	JwtTokenFilter jwtTokenFilter;

	  @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        // Enable CORS and disable CSRF
	        http = http.cors().and().csrf().disable();

	        // Set session management to stateless
	        http = http
	            .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and();

	        http = http
	            .exceptionHandling()
	            .authenticationEntryPoint(
	                (request, response, ex) -> {
	                    response.sendError(
	                        HttpServletResponse.SC_UNAUTHORIZED,
	                        ex.getMessage()
	                    );
	                }
	            )
	            .and();

	        // Set permissions on endpoints
	        http.authorizeRequests()
	            .antMatchers(HttpMethod.GET, "/**").permitAll()
	            .antMatchers(HttpMethod.POST, "/login").permitAll()
	            .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
	            .anyRequest().authenticated();

	        // Add JWT token filter
	        http.addFilterBefore(
	            jwtTokenFilter,
	            UsernamePasswordAuthenticationFilter.class
	        );
	    }

	    // Used by spring security if CORS is enabled
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(this.datasource)
		.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	
	@Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		//the below three lines will add the relevant CORS response headers
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
