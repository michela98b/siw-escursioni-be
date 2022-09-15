package it.uniroma3.siw.escursioni.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.escursioni.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
