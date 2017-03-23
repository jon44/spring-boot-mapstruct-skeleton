package cooksys.service;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import cooksys.dto.SimpleTweetDto;
import cooksys.dto.TweetRequestDto;
import cooksys.entity.Tweet;
import cooksys.mapper.TweetMapper;
import cooksys.repository.TweetRepository;
import cooksys.entity.embeddable.Credentials;


@Service
public class TweetService {
	
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private EntityManager entityManager;
	
	public TweetService(TweetRepository tweetRepository, TweetMapper tweetMapper, EntityManager entityManager) {
		super();
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.entityManager = entityManager;
	}

	public SimpleTweetDto postTweet(TweetRequestDto tweetRequestDto) {
		System.out.println("postTweet ---- " + tweetRequestDto.getCredentials().getUsername());
		//Tweet tweet = tweetMapper.toTweet(tweetRequestDto);
		//System.out.println(tweet.getAuthor().getCredentials().getUsername());
		return null;
	}
	
}
