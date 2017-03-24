package cooksys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cooksys.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
	
	boolean existsByLabel(String label);

}
