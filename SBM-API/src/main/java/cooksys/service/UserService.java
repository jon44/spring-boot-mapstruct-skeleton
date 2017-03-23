package cooksys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import cooksys.dto.UserDto;
import cooksys.dto.UserRequestDto;
import cooksys.entity.User;
import cooksys.entity.embeddable.Credentials;
import cooksys.mapper.UserMapper;
import cooksys.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private UserMapper userMapper;
	private EntityManager entityManager;
	
	public UserService(UserRepository userRepository, UserMapper userMapper, EntityManager entityManager) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

}
