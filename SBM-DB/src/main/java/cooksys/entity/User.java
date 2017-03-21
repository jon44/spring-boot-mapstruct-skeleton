package cooksys.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import cooksys.entity.embeddable.Credentials;
import cooksys.entity.embeddable.Profile;

@Entity
public class User {
	
	@Id
	@Column(unique = true, nullable = false)
	private String username;
	
	@NotNull
	private Credentials credentials;
	
	@NotNull
	private Profile profile;
	
	@ManyToMany(mappedBy = "following")
	private Set<User> followers;
	
	@ManyToMany
	private Set<User> following;
	
	private Set<Tweet> mentions;
	
	private Boolean deleted;
	
	public User() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public Set<Tweet> getMentions() {
		return mentions;
	}

	public void setMentions(Set<Tweet> mentions) {
		this.mentions = mentions;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
