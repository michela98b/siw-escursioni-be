package it.uniroma3.siw.escursioni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.escursioni.model.User;
import it.uniroma3.siw.escursioni.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		return this.userRepository.findById(id).get();
	}
}
