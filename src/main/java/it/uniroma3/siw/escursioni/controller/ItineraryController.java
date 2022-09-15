package it.uniroma3.siw.escursioni.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.escursioni.model.Itinerary;
import it.uniroma3.siw.escursioni.model.Path;
import it.uniroma3.siw.escursioni.service.ItineraryService;

@RestController
public class ItineraryController {
	
	@Autowired
	private ItineraryService itineraryService;
	
	
	@RequestMapping(value="itinerary/add", method = RequestMethod.POST)
	public void addItinerary(@RequestBody Itinerary itinerary) {
		this.itineraryService.save(itinerary);
	}
	
	@RequestMapping(value="itinerary/add/{id}", method = RequestMethod.POST)
	public void addItinerary(@RequestBody Path path, @PathVariable("id") Long id) {
		Itinerary it = this.itineraryService.findById(id);
		List<Path> paths = it.getPaths();
		paths.add(path);
		
		it.setPaths(paths);
		this.itineraryService.save(it);	
	}
	
}
