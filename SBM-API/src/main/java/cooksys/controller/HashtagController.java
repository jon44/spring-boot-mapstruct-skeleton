package cooksys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class HashtagController {

	@GetMapping
	public void getTags() {
		
	}
	
	@GetMapping("{label}")
	public void getTaggedBy(@PathVariable String label) {
		
	}
}
