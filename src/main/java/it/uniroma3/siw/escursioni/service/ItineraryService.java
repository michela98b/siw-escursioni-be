package it.uniroma3.siw.escursioni.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.escursioni.model.Itinerary;
import it.uniroma3.siw.escursioni.model.User;
import it.uniroma3.siw.escursioni.repository.ItineraryRepository;

@Service
public class ItineraryService {

	@Autowired
	protected ItineraryRepository itineraryRepository;

	public Itinerary findById(Long id) {
		return this.itineraryRepository.findById(id).get();
	}

	public List<Itinerary> findByUser(User user){
		return  this.itineraryRepository.findByTraveler(user);
	}

	@Transactional
	public void save(Itinerary itinerary) {
		this.itineraryRepository.save(itinerary);
	}
}
