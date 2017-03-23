package cooksys.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tweets")
public class TweetController {

	@GetMapping
	public void getTweets() {
		
	}
	
	@PostMapping
	public void postTweet() {
		
	}
	
	@GetMapping("{id}")
	public void getTweet(@PathVariable Long id) {
		
	}
	
	@DeleteMapping("{id}")
	public void deleteTweet(@PathVariable Long id) {
		
	}
	
	@PostMapping("{id}/like")
	public void likeTweet(@PathVariable Long id) {
		
	}
	
	@PostMapping("{id}/reply")
	public void replyTweet(@PathVariable Long id) {
		
	}
	
	@PostMapping("{id}/repost")
	public void repostTweet(@PathVariable Long id) {
		
	}
	
	@GetMapping("{id}/tags")
	public void getTags(@PathVariable Long id) {
		
	}
	
	@GetMapping("{id}/likes")
	public void getLikes(@PathVariable Long id) {
		
	}
	
	@GetMapping("{id}/context")
	public void getContext(@PathVariable Long id) {
		
	}
	
	@GetMapping("{id}/replies")
	public void getReplies(@PathVariable Long id) {
		
	}
	
	@GetMapping("{id}/reposts")
	public void getReposts(@PathVariable Long id) {
		
	}
	
	@GetMapping("{id}/mentions")
	public void getMentions(@PathVariable Long id) {
		
	}
	
}
