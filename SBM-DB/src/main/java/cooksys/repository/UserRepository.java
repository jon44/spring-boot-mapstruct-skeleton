package cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cooksys.entity.User;
import cooksys.entity.embeddable.Credentials;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByDeleted(Boolean deleted);
	
	User findByCredentialsUsername(String username);
	
	Boolean existsByCredentialsUsername(String username);
	
	User findByCredentials(Credentials credentials);
	
	Boolean existsByCredentials(Credentials credentials);
	
}
