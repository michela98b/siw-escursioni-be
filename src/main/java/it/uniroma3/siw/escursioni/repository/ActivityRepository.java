package it.uniroma3.siw.escursioni.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.escursioni.model.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long> {

}
