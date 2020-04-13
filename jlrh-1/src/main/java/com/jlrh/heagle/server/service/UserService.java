package com.jlrh.heagle.server.service;

import java.util.List;

import com.jlrh.heagle.server.dto.UserDto;


public interface UserService {
	
	List<UserDto> findAll();
	
    List<UserDto> findAlljpa();

	List<UserDto> findAlljpaId(String id);

	List<UserDto> findAlljdbc();

	List<UserDto> findAlljpa3(String id);
}
