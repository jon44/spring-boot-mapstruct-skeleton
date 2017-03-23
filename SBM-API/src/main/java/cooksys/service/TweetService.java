package cooksys.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import cooksys.dto.TweetDto;
import cooksys.dto.TweetRequestDto;
import cooksys.entity.Tweet;
import cooksys.mapper.TweetMapper;
import cooksys.repository.TweetRepository;
import cooksys.repository.UserRepository;


@Service
public class TweetService {
	
	private TweetRepository tweetRepository;
	private UserRepository userRepository;
	private TweetMapper tweetMapper;
	private EntityManager entityManager;
	
	public TweetService(TweetRepository tweetRepository, UserRepository userRepository, TweetMapper tweetMapper, EntityManager entityManager) {
		super();
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
		this.tweetMapper = tweetMapper;
		this.entityManager = entityManager;
	}

	public TweetDto postTweet(TweetRequestDto tweetRequestDto) {
		
		Tweet tweet = tweetMapper.toTweet(tweetRequestDto);
		tweet.setDeleted(false);
		tweet = tweetRepository.save(tweet);
		entityManager.detach(tweet);
		tweet = tweetRepository.findOne(tweet.getId());
		return tweetMapper.toSimpleTweetDto(tweet);
	}

	public TweetDto postReplyTweet(TweetRequestDto tweetRequestDto, Long id) {
		
		if(userRepository.existsByCredentials(tweetRequestDto.getCredentials())) {
			
			if(tweetRepository.existsByIdAndDeleted(id, false)) {
				Tweet replyTo = tweetRepository.findOne(id);				
				Tweet tweet = tweetMapper.toTweet(tweetRequestDto);
				List<Tweet> replies = replyTo.getReplies();
				replies.add(tweet);
				replyTo.setReplies(replies);
				tweet.setDeleted(false);
				tweet.setIsReplyTo(replyTo);
				tweet = tweetRepository.save(tweet);
				entityManager.detach(tweet);
				tweet = tweetRepository.findOne(tweet.getId());
				TweetDto tweetDto = tweetMapper.toReplyTweetDto(tweet);
				return tweetDto;
			} else {
				//tweet does not exist
			}
		} else {
			// credentials did not match
		}
		return null;
	}

	public TweetDto getTweet(Long id) {
		
		if(tweetRepository.existsByIdAndDeleted(id, false)){
			Tweet tweet = tweetRepository.findOne(id);
			if(tweet.getIsReplyTo() != null) {
				
			} else if(tweet.getRepostOf() != null) {
				
			} else {
				
			}
		}
		return null;
	}
	
}
