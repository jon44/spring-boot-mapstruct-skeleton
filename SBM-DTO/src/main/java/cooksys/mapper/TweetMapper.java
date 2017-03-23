package cooksys.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cooksys.dto.TweetRequestDto;
import cooksys.entity.Tweet;

@Mapper(componentModel = "spring", uses = {CredentialsMapper.class})
public interface TweetMapper {
	
	@Mappings({
		@Mapping(source = "content", target = "content")
		//@Mapping(source = "credentials", target = "author")
	})
	Tweet toTweet(TweetRequestDto tweetRequestDto);

}
