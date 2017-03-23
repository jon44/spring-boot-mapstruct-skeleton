package cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.dto.UserDto;
import cooksys.dto.UserRequestDto;
import cooksys.entity.embeddable.Credentials;
import cooksys.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping
	public List<UserDto> getUsers() {
		return userService.getUsers();
	}
	
	@PostMapping
	public UserDto postUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.postUser(userRequestDto);
	}
	
	@GetMapping("@{username}")
	public UserDto getUser(@PathVariable String username) {
		return userService.getUser(username);
	}
	
	@PatchMapping("@{username}")
	public UserDto patchUser(@RequestBody UserRequestDto userRequestDto, @PathVariable String username) {
		return userService.patchUser(userRequestDto, username);
	}
	
	@DeleteMapping("@{username}")
	public UserDto deleteUser(@RequestBody Credentials credentials, @PathVariable String username) {
		return userService.deleteUser(credentials, username);
	}
	
	@PostMapping("@{username}/follow")
	public void followUser(@PathVariable String username) {
		
	}
	
	@PostMapping("@{username}/unfollow")
	public void unfollowUser(@PathVariable String username) {
		
	}
	
	@GetMapping("@{username}/feed")
	public void getFeed(@PathVariable String username) {
		
	}
	
	@GetMapping("@{username}/tweets")
	public void getTweets(@PathVariable String username) {
		
	}
	
	@GetMapping("@{username}/mentions")
	public void getMentions(@PathVariable String username) {
		
	}
	
	@GetMapping("@{username}/followers")
	public void getFollowers(@PathVariable String username) {
		
	}
	
	@GetMapping("@{username}/following")
	public void getFollowing(@PathVariable String username) {
		
	}
	
}
