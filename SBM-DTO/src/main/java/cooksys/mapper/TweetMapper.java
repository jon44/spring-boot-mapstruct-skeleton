package cooksys.mapper;

import org.springframework.stereotype.Component;

import cooksys.dto.ReplyTweetDto;
import cooksys.dto.SimpleTweetDto;
import cooksys.dto.TweetDto;
import cooksys.dto.TweetRequestDto;
import cooksys.dto.UserDto;
import cooksys.entity.Tweet;
import cooksys.entity.User;
import cooksys.repository.UserRepository;

@Component
public class TweetMapper {
	
	private UserRepository userRepository;
	private UserMapper userMapper;
	
	public TweetMapper(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}
	
	public Tweet toTweet(TweetRequestDto tweetRequestDto) {
		
		Tweet tweet = new Tweet();
		User user = userRepository.findByCredentials(tweetRequestDto.getCredentials());
		tweet.setAuthor(user);
		tweet.setContent(tweetRequestDto.getContent());
		return tweet;
	}

	public TweetDto toTweetDto(Tweet tweet) {
		TweetDto tweetDto = new TweetDto();
		UserDto userDto = userMapper.toUserDto(tweet.getAuthor());
		tweetDto.setAuthor(userDto);
		tweetDto.setId(tweet.getId().intValue());
		tweetDto.setPosted(tweet.getPosted());
		return tweetDto;
	}

	public SimpleTweetDto toSimpleTweetDto(Tweet tweet) {
		
		SimpleTweetDto simpleTweetDto = new SimpleTweetDto();
		UserDto userDto = userMapper.toUserDto(tweet.getAuthor());
		simpleTweetDto.setAuthor(userDto);
		simpleTweetDto.setId(tweet.getId().intValue());
		simpleTweetDto.setPosted(tweet.getPosted());
		simpleTweetDto.setContent(tweet.getContent());
		return simpleTweetDto;
	}

	public ReplyTweetDto toReplyTweetDto(Tweet tweet) {
		
		ReplyTweetDto replyTweetDto = new ReplyTweetDto();
		UserDto userDto = userMapper.toUserDto(tweet.getAuthor());
		TweetDto tweetDto = toSimpleTweetDto(tweet.getIsReplyTo());
		replyTweetDto.setAuthor(userDto);
		replyTweetDto.setIsReplyTo(tweetDto);
		replyTweetDto.setId(tweet.getId().intValue());
		replyTweetDto.setPosted(tweet.getPosted());
		replyTweetDto.setContent(tweet.getContent());
		return replyTweetDto;
	}
	
}
