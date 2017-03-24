package cooksys.dto;

public class RepostTweetDto extends TweetDto {
	
	private TweetDto repostOf;

	public TweetDto getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(TweetDto repostOf) {
		this.repostOf = repostOf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((repostOf == null) ? 0 : repostOf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepostTweetDto other = (RepostTweetDto) obj;
		if (repostOf == null) {
			if (other.repostOf != null)
				return false;
		} else if (!repostOf.equals(other.repostOf))
			return false;
		return true;
	}
	
}
