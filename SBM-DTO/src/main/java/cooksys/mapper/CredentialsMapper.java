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
		
		System.out.println("credentialsMapper");
		if(userRepository.existsByCredentials(credentials)) {
			System.out.println("credentialsMapper - credentials validated");
			return userRepository.findByCredentials(credentials);
		}
		return null;
	}
	
}
