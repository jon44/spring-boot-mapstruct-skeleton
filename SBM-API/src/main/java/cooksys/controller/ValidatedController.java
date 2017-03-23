package cooksys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("validate/")
public class ValidatedController {

	@GetMapping("tag/exists/{label}")
	public void tagExists(@PathVariable String label) {
		
	}
	
	@GetMapping("username/exists/@{username}")
	public void usernameExists(@PathVariable String username) {
		
	}
	
	@GetMapping("username/available/@{username}")
	public void usernameAvailable(@PathVariable String username) {
		
	}
	
}
