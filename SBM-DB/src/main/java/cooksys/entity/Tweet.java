package cooksys.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import cooksys.entity.embeddable.Context;

@Entity
public class Tweet {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private User author;
	
	@NotNull
	private Timestamp posted;
	
	@NotNull
	private String content;
	
	@ManyToOne
	private Tweet isReplyTo;
	
	@ManyToOne
	private Tweet repostOf;
	
	private Context context;
	
	@OneToMany
	private List<Hashtag> hashtags;
	
	@OneToMany(mappedBy = "isReplyTo")
	private List<Tweet> replies;
	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;
	
	@ManyToMany
	private List<User> mentions;
	
	@OneToMany
	private List<User> likes;
	
	private boolean deleted;
	
	public Tweet() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Tweet getIsReplyTo() {
		return isReplyTo;
	}

	public void setIsReplyTo(Tweet isReplyTo) {
		this.isReplyTo = isReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}

	public List<Tweet> getReplies() {
		return replies;
	}

	public void setReplies(List<Tweet> replies) {
		this.replies = replies;
	}

	public List<Tweet> getReposts() {
		return reposts;
	}

	public void setReposts(List<Tweet> reposts) {
		this.reposts = reposts;
	}

	public List<User> getMentions() {
		return mentions;
	}

	public void setMentions(List<User> mentions) {
		this.mentions = mentions;
	}

	public List<User> getLikes() {
		return likes;
	}

	public void setLikes(List<User> likes) {
		this.likes = likes;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
