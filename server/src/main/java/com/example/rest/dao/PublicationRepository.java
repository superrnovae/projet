package com.example.rest.dao;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.rest.model.Publication;
import com.example.rest.model.User;

@Repository
@Transactional(readOnly = true)
public interface PublicationRepository extends JpaRepository<Publication, Long> {

	@Async
	CompletableFuture<Optional<Publication>> findOneById(final Long id);
	
	@Async
	CompletableFuture<Slice<Publication>> findAllByUtilisateurUsername(String username, Pageable paging);
	
	@Async
	@Query("SELECT p FROM Publication p JOIN FETCH p.comments WHERE p.id = (:id)")
    	Publication findByIdAndFetchCommentsEagerly(@Param("id") Long id);
	
	@Async
	@Query(value = "SELECT * from post p "
			+ "INNER JOIN follower f on p.user_id=f.to_id "
			+ "INNER JOIN utilisateur u on f.from_id=u.id "
			+ "WHERE u.username=:username", nativeQuery=true)
    	CompletableFuture<Slice<Publication>> findNewPublications(@Param("username") String username, Pageable page);
	
	@Async
	CompletableFuture<Long> countByUtilisateur(User user);
	
	@Query("SELECT COUNT(p)>0 from Publication p WHERE p.photo like (%:name)")
	boolean existsByFilepath(@Param("name") String filename);
	
	@Modifying
	@Transactional
	int deleteByIdAndUtilisateur(@Param("id") Long id, @Param("user") User user);
}
