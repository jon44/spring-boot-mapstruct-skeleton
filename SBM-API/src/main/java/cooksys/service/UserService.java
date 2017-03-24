package cooksys.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import cooksys.dto.TweetDto;
import cooksys.dto.UserDto;
import cooksys.dto.UserRequestDto;
import cooksys.entity.Tweet;
import cooksys.entity.User;
import cooksys.entity.embeddable.Credentials;
import cooksys.mapper.TweetMapper;
import cooksys.mapper.UserMapper;
import cooksys.repository.TweetRepository;
import cooksys.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private TweetRepository tweetRepository;
	private UserMapper userMapper;
	private TweetMapper tweetMapper;
	private EntityManager entityManager;
	
	public UserService(UserRepository userRepository, TweetRepository tweetRepository, UserMapper userMapper, TweetMapper tweetMapper, EntityManager entityManager) {
        super();
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.userMapper = userMapper;
        this.tweetMapper = tweetMapper;
        this.entityManager = entityManager;
    }
	
	 public List<UserDto> getUsers() {
		 
		 return userRepository
				 .findByDeleted(false)
	             .stream()
	             .map(userMapper::toUserDto)
	             .collect(Collectors.toList());
	 }

	public UserDto postUser(UserRequestDto userRequestDto) {
		
		User user = userMapper.toUser(userRequestDto);
		user.setDeleted(false);
		user = userRepository.saveAndFlush(user);
		entityManager.detach(user);
		user = userRepository.findOne(user.getId());
		return userMapper.toUserDto(user);
	}

	public UserDto getUser(String username) {
		
		User user = userRepository.findByCredentialsUsername(username);
		return userMapper.toUserDto(user);
	}
	
	public UserDto patchUser(UserRequestDto userRequestDto, String username) {
		
		if(userRepository.existsByCredentialsUsername(username)){
			User user = userRepository.findByCredentialsUsername(username);
			if(user.getCredentials().getUsername().equals(username) && !user.getDeleted()) {
				user.setProfile(userRequestDto.getProfile());
				return userMapper.toUserDto(userRepository.save(user));
			}
		}
		return null;
	}

	public UserDto deleteUser(Credentials credentials, String username) {

		if(userRepository.existsByCredentials(credentials)) {
			User user = userRepository.findByCredentials(credentials);
			if(user.getCredentials().getUsername().equals(username)) {
				user.setDeleted(true);
				user = userRepository.save(user);
				return userMapper.toUserDto(user);
			} else {
				//name does not match credentials
			}
		} else {
			//credentials do not belong to any user
		}
		return null;
	}

	public void followUser(Credentials credentials, String username) {
		
		if(userRepository.existsByCredentials(credentials)) {
			User user = userRepository.findByCredentials(credentials);
			User toFollow = userRepository.findByCredentialsUsername(username);
			
			Set<User> following = user.getFollowing();
			following.add(toFollow);
			user.setFollowing(following);
			user = userRepository.save(user);
			
			Set<User> followers = toFollow.getFollowers();
			followers.add(user);
			toFollow.setFollowers(followers);
			toFollow = userRepository.save(toFollow);
		} else {
			//credentials do not belong to any user
		}
	}

	public void unfollowUser(Credentials credentials, String username) {
		
		if(userRepository.existsByCredentials(credentials)) {
			User user = userRepository.findByCredentials(credentials);
			User toUnfollow = userRepository.findByCredentialsUsername(username);
			
			Set<User> following = user.getFollowing();
			following.remove(toUnfollow);
			user.setFollowing(following);
			user = userRepository.save(user);
			
			Set<User> followers = toUnfollow.getFollowers();
			followers.remove(user);
			toUnfollow.setFollowers(followers);
			toUnfollow = userRepository.save(toUnfollow);
		} else {
			//credentials do not belong to any user
		}
	}

	public Set<UserDto> getFollowers(String username) {
		
		if(userRepository.existsByCredentialsUsername(username)) {
			User user = userRepository.findByCredentialsUsername(username);
			Set<User> followers = user.getFollowers();
			Set<UserDto> followersDto = new HashSet<>();
			for(User i : followers) {
				followersDto.add(userMapper.toUserDto(i));
			}
			return followersDto;
		} else {
			//user does not exist
		}
		return null;
	}

	public Set<UserDto> getFollowing(String username) {
		
		if(userRepository.existsByCredentialsUsername(username)) {
			User user = userRepository.findByCredentialsUsername(username);
			Set<User> following = user.getFollowing();
			Set<UserDto> followingDto = new HashSet<>();
			for(User i : following) {
				followingDto.add(userMapper.toUserDto(i));
			}
			return followingDto;
		} else {
			//user does not exist
		}
		return null;
	}

	public List<TweetDto> getTweets(String username) {
		
		if(userRepository.existsByCredentialsUsername(username)) {
			User user = userRepository.findByCredentialsUsername(username);
			List<Tweet> tweets = user.getTweets();
			List<TweetDto> tweetsDto = new ArrayList<>();
			List<TweetDto> sortedTweetsDto = new ArrayList<>();
			
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
			
			tweetsDto
			.stream()
			.sorted((tweet1, tweet2) -> tweet2.getPosted()
	                .compareTo(tweet1.getPosted()))
	        .forEach(tweet -> sortedTweetsDto.add(tweet));
			
			return sortedTweetsDto;
		}
		return null;
	}

	public List<TweetDto> getMentions(String username) {
		
		List<Tweet> tweets = new ArrayList<>();
		List<TweetDto> tweetsDto = new ArrayList<>();
		User user = userRepository.findByCredentialsUsername(username);
		tweets = user.getMentioned();
		
		for(Tweet i : tweets) {
			tweetsDto.add(tweetMapper.toTweetDto(i));
		}
		
		return tweetsDto;
	}

}
