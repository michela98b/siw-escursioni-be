package it.uniroma3.siw.escursioni.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.escursioni.model.Place;

public interface PlaceRepository extends CrudRepository<Place, Long> {

}
