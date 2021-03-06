package com.example.rest.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.rest.model.Comment;
import com.example.rest.model.Publication;


@Repository
@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	int countByPost(Publication p);
	
	Page<Comment> findAllByPostId(Long id, Pageable paging);
	
	@Modifying
	@Query(	value = "DELETE c.* from comment c INNER JOIN post p on c.post_id=p.id INNER JOIN utilisateur u on c.user_id=u.id OR p.user_id=u.id WHERE c.id=:id AND u.username=:username", 
			nativeQuery = true)
	int delete(@Param("id") Long id, @Param("username") String username);
	
	
}
