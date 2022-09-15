package it.uniroma3.siw.escursioni.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.escursioni.model.Activity;
import it.uniroma3.siw.escursioni.service.ActivityService;

@RestController
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;

	@RequestMapping(value="/activity/all", method = RequestMethod.GET)
	public List<Activity> getActivities() {
		return this.activityService.findAll();
	}
	
	@RequestMapping(value="/activity/{id}", method = RequestMethod.GET)
	public Activity getActivity(@PathVariable("id") Long id) {
		return this.activityService.findById(id);
	}
	
	@RequestMapping(value="admin/activity/add", method = RequestMethod.POST)
	public void addActivity(@RequestBody Activity activity) {
		this.activityService.save(activity);
	}
	
	@RequestMapping(value="admin/activity/delete/{id}", method = RequestMethod.GET)
	public void deleteActivity(@PathVariable("id") Long id) {
		this.activityService.deleteById(id);
	}
}
