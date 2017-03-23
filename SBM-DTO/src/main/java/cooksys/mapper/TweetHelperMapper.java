package cooksys.mapper;

import org.springframework.stereotype.Component;

import cooksys.dto.TweetRequestDto;
import cooksys.entity.Tweet;
import cooksys.entity.User;
import cooksys.repository.UserRepository;

@Component
public class TweetHelperMapper {
	
	private UserMapper userMapper;
	private UserRepository userRepository;
	
	public TweetHelperMapper(TweetMapper tweetMapper) {
		this.userMapper = userMapper;
		this.userRepository = userRepository;
	}
	
	public Tweet toTweet(TweetRequestDto tweetRequestDto) {
		System.out.println("\n\n\n\ntop of toTweet\n\n\n\n");
		Tweet tweet = new Tweet();
		tweet.setContent(tweetRequestDto.getContent());
		User user = userRepository.findByCredentials(tweetRequestDto.getCredentials());
		tweet.setAuthor(user);
		return tweet;
	}
	
//	public TweetDto toTweetDto(Tweet tweet) {
//		
//		if(tweet.getIsReplyTo() != null) {
//			ReplyTweetDto replyTweetDto = tweetMapper.toReplyTweetDto(tweet);
//			return replyTweetDto;
//		} else if(tweet.getRepostOf() != null) {
//			
//		} else {
//			SimpleTweetDto simpleTweetDto = tweetMapper.toSimpleTweetDto(tweet);
//			return simpleTweetDto;
//		}
//		
//		return null;
//	}

}
