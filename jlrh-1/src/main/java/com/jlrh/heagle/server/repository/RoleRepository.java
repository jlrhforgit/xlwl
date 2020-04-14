package com.jlrh.heagle.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jlrh.heagle.server.domain.URole;

public interface RoleRepository extends JpaRepository<URole, Object> {
	
	@Query(value = "select * from urole where id in(select rid from uuser_role where uid =?1 )", nativeQuery = true)
	List<URole> getRolesByUserId(Long Id);
}
