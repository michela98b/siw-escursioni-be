package it.uniroma3.siw.escursioni.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.escursioni.authentication.JwtTokenUtil;
import it.uniroma3.siw.escursioni.model.Credentials;
import it.uniroma3.siw.escursioni.model.User;
import it.uniroma3.siw.escursioni.service.CredentialsService;
import it.uniroma3.siw.escursioni.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CredentialsService credentialsService;
	

	@RequestMapping(value="/user/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable("id") Long id) {
		return this.userService.findById(id);
	}

	@RequestMapping(value="/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> login(@RequestBody Credentials credentials) {	
		
		Credentials userCredentials = this.credentialsService.getCredentials(credentials.getUsername());
		try {

            User user = userCredentials.getUser();
            
            Map<String, Object> rtn = new LinkedHashMap<>();

            rtn.put("user", user);
            rtn.put("role", userCredentials.getRole());

            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,  
                    jwtTokenUtil.generateAccessToken(userCredentials)
                ).header("access-control-expose-headers", "Authorization")
                .body(rtn);
            
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	}

}
