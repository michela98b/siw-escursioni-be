package it.uniroma3.siw.escursioni.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.escursioni.model.Place;
import it.uniroma3.siw.escursioni.repository.PlaceRepository;

@Service
public class PlaceService {

	@Autowired
	private PlaceRepository placeRepository;
	
	public List<Place> findAll(){
		
		List<Place> places = new ArrayList<Place>();
		
		for(Place p: this.placeRepository.findAll()) {
			places.add(p);
		}
		return places;
	}
	
	@Transactional
	public void save(Place place) {
		this.placeRepository.save(place);
	}
	
}
