package cooksys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cooksys.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
