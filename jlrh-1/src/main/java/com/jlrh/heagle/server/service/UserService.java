package com.jlrh.heagle.server.service;

import java.util.List;

import com.jlrh.heagle.server.dto.UserDTO;


public interface UserService {
	
	List<UserDTO> findAll();
	
    List<UserDTO> findAlljpa();

	List<UserDTO> findAlljpaId(String id);

	List<UserDTO> findAlljdbc();

	List<UserDTO> findAlljpa3(String id);
}
