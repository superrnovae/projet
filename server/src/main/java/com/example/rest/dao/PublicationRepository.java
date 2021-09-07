package com.example.rest.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.rest.model.Publication;
import com.example.rest.model.User;

@Repository
@Transactional(readOnly = true)
public interface PublicationRepository extends JpaRepository<Publication, Long> {

	Slice<Publication> findAllByUtilisateurUsername(String username, Pageable paging);
	
	@Query("SELECT p FROM Publication p JOIN FETCH p.comments WHERE p.id = (:id)")
    Publication findByIdAndFetchCommentsEagerly(@Param("id") Long id);
	
	Long countByUtilisateur(User user);
	
	@Query("SELECT COUNT(p)>0 from Publication p WHERE p.photo like %:name")
	boolean existsByFilepath(@Param("name") String filename);
	
	@Modifying
	@Transactional
	int deleteByIdAndUtilisateur(@Param("id") Long id, @Param("user") User user);
}