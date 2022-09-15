package it.uniroma3.siw.escursioni.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.escursioni.model.Activity;
import it.uniroma3.siw.escursioni.repository.ActivityRepository;


@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;
	
	public List<Activity> findAll(){
		
		List<Activity> activities = new ArrayList<Activity>();
		
		for(Activity a: this.activityRepository.findAll()) {
			activities.add(a);
		}
		return activities;
	}
	
	public Activity findById(Long id) {
		return this.activityRepository.findById(id).get();
	}
	
	@Transactional
	public void save(Activity activity) {
		this.activityRepository.save(activity);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.activityRepository.deleteById(id);
	}
	
}
