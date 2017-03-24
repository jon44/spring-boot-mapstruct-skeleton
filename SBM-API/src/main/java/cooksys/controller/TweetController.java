package cooksys.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.dto.TweetDto;
import cooksys.dto.TweetRequestDto;
import cooksys.entity.embeddable.Credentials;
import cooksys.service.TweetService;

@RestController
@RequestMapping("tweets")
public class TweetController {
	
	private TweetService tweetService;
	
	public TweetController(TweetService tweetService) {
		super();
		this.tweetService = tweetService;
	}

	@GetMapping
	public void getTweets() {
		
	}
	
	@PostMapping
	public TweetDto postTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.postTweet(tweetRequestDto);
	}
	
	@GetMapping("{id}")
	public TweetDto getTweet(@PathVariable Long id) {
		return tweetService.getTweet(id);
	}
	
	@DeleteMapping("{id}")
	public void deleteTweet(@PathVariable Long id) {
		
	}
	
	@PostMapping("{id}/like")
	public void likeTweet(@PathVariable Long id) {
		
	}
	
	@PostMapping("{id}/reply")
	public TweetDto replyTweet(@RequestBody TweetRequestDto tweetRequestDto, @PathVariable Long id) {
		return tweetService.postReplyTweet(tweetRequestDto, id);
	}
	
	@PostMapping("{id}/repost")
	public TweetDto repostTweet(@RequestBody Credentials credentials, @PathVariable Long id) {
		return tweetService.postRepostTweet(credentials, id);
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
