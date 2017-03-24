package cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.dto.TweetDto;
import cooksys.dto.TweetRequestDto;
import cooksys.dto.UserDto;
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
	public List<TweetDto> getTweets() {
		return tweetService.getTweets();
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
	public TweetDto deleteTweet(@RequestBody Credentials credentials, @PathVariable Long id) {
		return tweetService.deleteTweet(credentials, id);
	}
	
	@PostMapping("{id}/like")
	public void likeTweet(@RequestBody Credentials credentials, @PathVariable Long id) {
		tweetService.likeTweet(credentials, id);
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
	public List<UserDto> getLikes(@PathVariable Long id) {
		return tweetService.getLikes(id);
	}
	
	@GetMapping("{id}/context")
	public void getContext(@PathVariable Long id) {
		
	}
	
	@GetMapping("{id}/replies")
	public List<TweetDto> getReplies(@PathVariable Long id) {
		return tweetService.getReplies(id);
	}
	
	@GetMapping("{id}/reposts")
	public List<TweetDto> getReposts(@PathVariable Long id) {
		return tweetService.getReposts(id);
	}
	
	@GetMapping("{id}/mentions")
	public void getMentions(@PathVariable Long id) {
		
	}
	
}
