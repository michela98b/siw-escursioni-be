package it.uniroma3.siw.escursioni.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.escursioni.model.Path;
import it.uniroma3.siw.escursioni.service.PathService;

@RestController
public class PathController {

	@Autowired
	private PathService pathService;

	@RequestMapping(value="/path/all", method = RequestMethod.GET)
	public List<Path> getPercorsi() {
		return this.pathService.findAll();
	}
	
	@RequestMapping(value="/path/{id}", method = RequestMethod.GET)
	public Path getPercorso(@PathVariable("id") Long id) {
		return this.pathService.findById(id);
	}

	@RequestMapping(value="admin/path/add", method = RequestMethod.POST)
	public void addPercorso(@RequestBody Path path) {
		this.pathService.save(path);
	}
	
	@RequestMapping(value="admin/path/delete/{id}", method = RequestMethod.GET)
	public void deletePercorso(@PathVariable("id") Long id) {
		this.pathService.deleteById(id);
	}
	
	

}
