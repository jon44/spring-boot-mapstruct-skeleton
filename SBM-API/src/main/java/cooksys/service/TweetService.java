package cooksys.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import cooksys.dto.TweetDto;
import cooksys.dto.TweetRequestDto;
import cooksys.dto.UserDto;
import cooksys.entity.Hashtag;
import cooksys.entity.Tweet;
import cooksys.entity.User;
import cooksys.entity.embeddable.Credentials;
import cooksys.mapper.TweetMapper;
import cooksys.mapper.UserMapper;
import cooksys.repository.HashtagRepository;
import cooksys.repository.TweetRepository;
import cooksys.repository.UserRepository;


@Service
public class TweetService {
	
	private TweetRepository tweetRepository;
	private UserRepository userRepository;
	private HashtagRepository hashtagRepository;
	private TweetMapper tweetMapper;
	private UserMapper userMapper;
	private EntityManager entityManager;
	
	public TweetService(TweetRepository tweetRepository, UserRepository userRepository, HashtagRepository hashtagRepository, TweetMapper tweetMapper, UserMapper userMapper, EntityManager entityManager) {
		super();
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
		this.hashtagRepository = hashtagRepository;
		this.tweetMapper = tweetMapper;
		this.userMapper = userMapper;
		this.entityManager = entityManager;
	}

	public TweetDto postTweet(TweetRequestDto tweetRequestDto) {
		
		Tweet tweet = tweetMapper.toTweet(tweetRequestDto);
		List<User> usersMentioned = parseForUsers(tweet.getContent());
		List<User> mentions = tweet.getMentions();
		if(mentions == null) {
			mentions = new ArrayList<>();
		}
		for(User i : usersMentioned) {
			mentions.add(i);
			List<Tweet> mentioned = i.getMentioned();
			if(mentioned == null) {
				mentioned = new ArrayList<>();
			}
			mentioned.add(tweet);
			i.setMentioned(mentioned);
		}
		tweet.setMentions(mentions);
		User author = tweet.getAuthor();
		List<Tweet> tweets = author.getTweets();
		tweets.add(tweet);
		author.setTweets(tweets);
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
				List<User> usersMentioned = parseForUsers(tweet.getContent());
				List<User> mentions = tweet.getMentions();
				if(mentions == null) {
					mentions = new ArrayList<>();
				}
				for(User i : usersMentioned) {
					mentions.add(i);
					List<Tweet> mentioned = i.getMentioned();
					if(mentioned == null) {
						mentioned = new ArrayList<>();
					}
					mentioned.add(tweet);
					i.setMentioned(mentioned);
				}
				tweet.setMentions(mentions);
				User author = tweet.getAuthor();
				List<Tweet> tweets = author.getTweets();
				tweets.add(tweet);
				author.setTweets(tweets);
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
				return tweetMapper.toReplyTweetDto(tweet);
			} else if(tweet.getRepostOf() != null) {
				return tweetMapper.toRepostTweetDto(tweet);
			} else {
				return tweetMapper.toSimpleTweetDto(tweet);
			}
		}
		return null;
	}

	public TweetDto postRepostTweet(Credentials credentials, Long id) {
		
		if(userRepository.existsByCredentials(credentials)) {
			if(tweetRepository.existsByIdAndDeleted(id, false)) {
				Tweet tweet = new Tweet();
				Tweet repostOf = tweetRepository.findOne(id);
				List<Tweet> reposts = repostOf.getReposts();
				reposts.add(tweet);
				repostOf.setReposts(reposts);
				tweet.setAuthor(userRepository.findByCredentials(credentials));
				User author = tweet.getAuthor();
				List<Tweet> tweets = author.getTweets();
				tweets.add(tweet);
				author.setTweets(tweets);
				tweet.setDeleted(false);
				tweet.setRepostOf(repostOf);
				tweet.setContent(repostOf.getContent());
				tweet = tweetRepository.save(tweet);
				entityManager.detach(tweet);
				tweet = tweetRepository.findOne(tweet.getId());
				TweetDto tweetDto = tweetMapper.toRepostTweetDto(tweet);
				return tweetDto;
			}
		}
		return null;
	}

	public List<TweetDto> getTweets() {
		
		List<Tweet> tweets = tweetRepository.findByDeleted(false);
		List<Tweet> sortedTweets = new ArrayList<>();
		List<TweetDto> sortedTweetsDto = new ArrayList<>();
		
		tweets
		.stream()
		.sorted((tweet1, tweet2) -> tweet2.getPosted()
                .compareTo(tweet1.getPosted()))
        .forEach(tweet -> sortedTweets.add(tweet));
		
		for(Tweet tweet : sortedTweets) {
			if(tweet.getIsReplyTo() != null) {
				sortedTweetsDto.add(tweetMapper.toReplyTweetDto(tweet));
			} else if(tweet.getRepostOf() != null) {
				sortedTweetsDto.add(tweetMapper.toRepostTweetDto(tweet));
			} else {
				sortedTweetsDto.add(tweetMapper.toSimpleTweetDto(tweet));
			}
		}
		return sortedTweetsDto;
	}

	public TweetDto deleteTweet(Credentials credentials, Long id) {
		
		if(userRepository.existsByCredentials(credentials)) {
			if(tweetRepository.existsByIdAndDeleted(id, false)) {
				Tweet tweet = tweetRepository.findOne(id);
				TweetDto tweetDto = new TweetDto();
				
				if(tweet.getIsReplyTo() != null) {
					tweetDto = tweetMapper.toReplyTweetDto(tweet);
				} else if(tweet.getRepostOf() != null) {
					tweetDto = tweetMapper.toRepostTweetDto(tweet);
				} else {
					tweetDto = tweetMapper.toSimpleTweetDto(tweet);
				}
				
				tweet.setDeleted(true);
				tweetRepository.save(tweet);
				return tweetDto;
				
			}
		}
		return null;
	}

	public void likeTweet(Credentials credentials, Long id) {
		
		if(userRepository.existsByCredentials(credentials)) {
			if(tweetRepository.existsByIdAndDeleted(id, false)) {
				User user = userRepository.findByCredentials(credentials);
				Tweet tweet = tweetRepository.findOne(id);
				List<User> likes = tweet.getLikes();
				likes.add(user);
				tweet.setLikes(likes);
				tweet = tweetRepository.save(tweet);
			}
		}
		
	}

	public List<UserDto> getLikes(Long id) {
		
		if(tweetRepository.existsByIdAndDeleted(id, false)) {
			Tweet tweet = tweetRepository.findOne(id);
			List<UserDto> userDtos = new ArrayList<>();
			List<User> likes = tweet.getLikes();
			
			for(User i : likes) {
				userDtos.add(userMapper.toUserDto(i));
			}
			return userDtos;
		}
		return null;
	}

	public List<TweetDto> getReplies(Long id) {
		
		if(tweetRepository.existsByIdAndDeleted(id, false)) {
			Tweet tweet = tweetRepository.findOne(id);
			List<Tweet> tweets = tweet.getReplies();
			List<TweetDto> tweetsDto = new ArrayList<>();
			
			for(Tweet i : tweets) {
				if(i.isDeleted() == false) {
					if(i.getIsReplyTo() != null) {
						tweetsDto.add(tweetMapper.toReplyTweetDto(i));
					} else if(i.getRepostOf() != null) {
						tweetsDto.add(tweetMapper.toRepostTweetDto(i));
					} else {
						tweetsDto.add(tweetMapper.toSimpleTweetDto(i));
					}
				}
			}
			return tweetsDto;
		}
		return null;
	}

	public List<TweetDto> getReposts(Long id) {
		
		if(tweetRepository.existsByIdAndDeleted(id, false)) {
			Tweet tweet = tweetRepository.findOne(id);
			List<Tweet> tweets = tweet.getReposts();
			List<TweetDto> tweetsDto = new ArrayList<>();
			
			for(Tweet i : tweets) {
				if(i.isDeleted() == false) {
					if(i.getIsReplyTo() != null) {
						tweetsDto.add(tweetMapper.toReplyTweetDto(i));
					} else if(i.getRepostOf() != null) {
						tweetsDto.add(tweetMapper.toRepostTweetDto(i));
					} else {
						tweetsDto.add(tweetMapper.toSimpleTweetDto(i));
					}
				}

			}
			return tweetsDto;
		}
		return null;
	}
	
	public List<User> parseForUsers(String content) {
		
		List<User> mentions = new ArrayList<>();
		
		String[] firstSplit = content.split("@");
		for(String i : firstSplit) {
			if(i != firstSplit[0]) {
				String[] secondSplit = i.split(" ");
				for(String j : secondSplit) {
					if(j == secondSplit[0]) {
						if(userRepository.existsByCredentialsUsername(j)) {
							mentions.add(userRepository.findByCredentialsUsername(j));
						}
					}
				}
			}
		}
		
		return mentions;
	}
	
	public List<Hashtag> parseForTags(String content) {
		
		List<Hashtag> tags = new ArrayList<>();
		
		String[] firstSplit = content.split("#");
		for(String i : firstSplit) {
			if(i != firstSplit[0]) {
				String[] secondSplit = i.split(" ");
				for(String j : secondSplit) {
					if(j == secondSplit[0]) {
						if(!hashtagRepository.existsByLabel(j)) {
							
						}
					}
				}
			}
		}
		return null;
	}

	public List<UserDto> getMentions(Long id) {
		
		Tweet tweet = tweetRepository.findOne(id);
		List<UserDto> usersDto = new ArrayList<>();
		List<User> users = tweet.getMentions();
		if(users == null) {
			users = new ArrayList<>();
		}
		
		for(User i : users) {
			usersDto.add(userMapper.toUserDto(i));
		}
		
		return usersDto;
	}
}
