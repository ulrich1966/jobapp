package de.juli.jobrest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
	

   @Autowired
  // private JobRepository repository;

    // Find
    @GetMapping("/jobs")
    public String findAll() {
  	return "hello";
    }
}
