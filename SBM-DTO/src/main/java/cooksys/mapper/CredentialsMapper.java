package cooksys.mapper;

import org.springframework.stereotype.Component;

import cooksys.entity.User;
import cooksys.entity.embeddable.Credentials;
import cooksys.repository.UserRepository;

@Component
public class CredentialsMapper {
	
	private UserRepository userRepository;
	
	public CredentialsMapper(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User credentialsToUser(Credentials credentials) {
		
		if(userRepository.existsByCredentials(credentials)) {
			return userRepository.findByCredentials(credentials);
		}
		return null;
	}
	
}
