package cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cooksys.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
	
	List<Tweet> findByDeleted(Boolean deleted);
	
	Boolean existsByIdAndDeleted(Long id, Boolean deleted);

}
