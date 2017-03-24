package cooksys.service;

import org.springframework.stereotype.Service;

import cooksys.entity.User;
import cooksys.repository.UserRepository;
import cooksys.repository.ValidatedRepository;

@Service
public class ValidatedService {
	
	private UserRepository userRepository;
	private ValidatedRepository validatedRepository;
	
	public ValidatedService(UserRepository userRepository, ValidatedRepository validatedRepository) {
		super();
		this.userRepository = userRepository;
		this.validatedRepository = validatedRepository;
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

	public boolean labelExists(String label) {
		
		if(validatedRepository.existsByLabel(label)) {
			return true;
		}
		return false;
	}

}
