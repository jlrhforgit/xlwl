package com.jlrh.heagle.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlrh.heagle.server.domain.User;

@Repository  
public interface UserRepository extends JpaRepository<User, Object> { 
	
	List<User> findAll();
	
	@Query(value = "select * from tb_user where id = ?1", nativeQuery = true)
	List<User> findbyId(String id);
	
	@Query(value = "select * from tb_user where id = ?1", nativeQuery = true)
	List<User> finddtobyId(String id);

	@Query(value = "select * from tb_user where username like concat('%',?1,'%')", nativeQuery = true)
//	@Query(value = "select * from tb_user where username like %?1%", nativeQuery = true)
	User getbyName(String userName);
}