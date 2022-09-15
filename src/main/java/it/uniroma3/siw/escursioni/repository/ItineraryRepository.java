package it.uniroma3.siw.escursioni.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.escursioni.model.Itinerary;
import it.uniroma3.siw.escursioni.model.User;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {

	public List<Itinerary> findByTraveler(User user);

}
