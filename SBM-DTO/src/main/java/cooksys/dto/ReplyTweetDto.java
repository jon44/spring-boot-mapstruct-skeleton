package cooksys.dto;

public class ReplyTweetDto extends TweetDto{
	
	private String content;
	
	private TweetDto isReplyTo;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TweetDto getIsReplyTo() {
		return isReplyTo;
	}

	public void setIsReplyTo(TweetDto isReplyTo) {
		this.isReplyTo = isReplyTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((isReplyTo == null) ? 0 : isReplyTo.hashCode());
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
		ReplyTweetDto other = (ReplyTweetDto) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (isReplyTo == null) {
			if (other.isReplyTo != null)
				return false;
		} else if (!isReplyTo.equals(other.isReplyTo))
			return false;
		return true;
	}
	
}
