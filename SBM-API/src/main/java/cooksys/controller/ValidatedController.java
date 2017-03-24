package cooksys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.service.ValidatedService;

@RestController
@RequestMapping("validate/")
public class ValidatedController {
	
	private ValidatedService validatedService;
	
	public ValidatedController(ValidatedService validatedService) {
		super();
		this.validatedService = validatedService;
	}

	@GetMapping("tag/exists/{label}")
	public void tagExists(@PathVariable String label) {
		
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean usernameExists(@PathVariable String username) {
		return validatedService.usernameExists(username);
	}
	
	@GetMapping("username/available/@{username}")
	public boolean usernameAvailable(@PathVariable String username) {
		return validatedService.usernameAvailable(username);
	}
	
}
