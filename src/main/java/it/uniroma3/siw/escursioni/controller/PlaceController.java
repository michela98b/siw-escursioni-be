package it.uniroma3.siw.escursioni.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.escursioni.model.Place;
import it.uniroma3.siw.escursioni.service.PlaceService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PlaceController {

	@Autowired
	private PlaceService placeService;

	@RequestMapping(value="/place/all", method = RequestMethod.GET)
	public List<Place> getLuoghi() {
		return this.placeService.findAll();
	}

	@RequestMapping(value="admin/place/add", method = RequestMethod.POST)
	public void addLuogo(@RequestBody Place place) {
		this.placeService.save(place);
	}

}
