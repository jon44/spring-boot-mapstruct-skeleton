package cooksys.service;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import cooksys.dto.ReplyTweetDto;
import cooksys.dto.SimpleTweetDto;
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

	public SimpleTweetDto postTweet(TweetRequestDto tweetRequestDto) {
		
		Tweet tweet = tweetMapper.toTweet(tweetRequestDto);
		tweet.setDeleted(false);
		tweet = tweetRepository.save(tweet);
		entityManager.detach(tweet);
		tweet = tweetRepository.findOne(tweet.getId());
		return tweetMapper.toSimpleTweetDto(tweet);
	}

	public ReplyTweetDto postReplyTweet(TweetRequestDto tweetRequestDto, Long id) {
		
		if(userRepository.existsByCredentials(tweetRequestDto.getCredentials())) {
			
			if(tweetRepository.existsByIdAndDeleted(id, false)) {
				Tweet tweet = tweetMapper.toTweet(tweetRequestDto);
				tweet.setDeleted(false);
				tweet.setIsReplyTo(tweetRepository.findOne(id));
			} else {
				//tweet does not exist
			}
		} else {
			// credentials did not match
		}
		return null;
	}
	
}
