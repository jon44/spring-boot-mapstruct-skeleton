package cooksys.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import cooksys.entity.embeddable.Context;

@Entity
public class Tweet {

	@Id
	@GeneratedValue
	private Integer id;
	
	private User author;
	
	private Timestamp posted;
	
	private String content;
	
	private Tweet isReplyTo;
	
	private Tweet repostOf;
	
	private Context context;
	
	private List<Hashtag> hashtags;
	
	private List<Tweet> replies;
	
	private List<Tweet> reposts;
	
}
