package cooksys.service;

import org.springframework.stereotype.Service;

import cooksys.entity.User;
import cooksys.repository.UserRepository;

@Service
public class ValidatedService {
	
	private UserRepository userRepository;
	
	public ValidatedService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public boolean usernameExists(String username) {
		
		if(userRepository.existsByCredentialsUsername(username)) {
			User user = userRepository.findByCredentialsUsername(username);
			if(user.getDeleted() == false)
				return true;
		}
		return false;
	}

	public boolean usernameAvailable(String username) {
		
		if(userRepository.existsByCredentialsUsername(username)) {
			return false;
		}
		return true;
	}

}
