package cooksys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cooksys.entity.Hashtag;

public interface ValidatedRepository extends JpaRepository<Hashtag, Long> {
	
	Boolean existsByLabel(String label);

}
