package com.example.web.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.rest.model.Comment;
import com.example.rest.model.Publication;
import com.example.rest.model.User;
import com.example.web.dto.response.CommentResDto;
import com.example.web.dto.response.PublicationDto;
import com.example.web.dto.response.UserDto;

@Mapper
public interface MapstructMapper {
	
	/* Comments */
	CommentResDto commToCommDto(Comment comment);
	List<CommentResDto> commListMap(List<Comment> comments);
	
	/* Publication */
	PublicationDto pubToPubDto(Publication publication);
	List<PublicationDto> listPubToListPubDto(List<Publication> publications);
	
	/* Utilisateur */
	UserDto userToUserDto(User user);
}
