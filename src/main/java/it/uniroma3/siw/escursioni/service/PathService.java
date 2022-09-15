package it.uniroma3.siw.escursioni.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.escursioni.model.Path;
import it.uniroma3.siw.escursioni.repository.PathRepository;

@Service
public class PathService {

	@Autowired
	private PathRepository pathRepository;

	public List<Path> findAll(){

		List<Path> paths = new ArrayList<Path>();

		for(Path p: this.pathRepository.findAll()) {
			paths.add(p);
		}
		return paths;
	}
	
	public Path findById(Long id) {
		return this.pathRepository.findById(id).get();
	}

	@Transactional
	public void save(Path path) {
		this.pathRepository.save(path);
	}

	@Transactional
	public void deleteById(Long id) {
		this.pathRepository.deleteById(id);
	}

}
