package com.jlrh.heagle.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlrh.heagle.server.domain.User;
import com.jlrh.heagle.server.dto.UserDto;
import com.jlrh.heagle.server.jdbctemplate.UserDao;
import com.jlrh.heagle.server.mybatismapper.UserMapper;
import com.jlrh.heagle.server.repository.UserRepository;
import com.jlrh.heagle.server.service.UserService;
import com.jlrh.heagle.server.utils.PropertyCopy;

@Service("userService")
public class UserServiceimpl implements UserService {
	
	private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired 
    private UserRepository repository; 
    @Autowired 
    private UserDao userDao; 
    
    @Override
    public List<UserDto> findAll() {
    	List<User> userList = userMapper.findAll();
    	List<UserDto> dtoList  =new ArrayList<UserDto>();
    	log.info("复制实体类属性到dto开始。。。");
    	PropertyCopy.populateList(userList, dtoList, UserDto.class);
        return dtoList;
    }
    @Override
    public List<UserDto> findAlljpa() {
    	List<User> userList = repository.findAll();
    	List<UserDto> dtoList  =new ArrayList<UserDto>();
    	dtoList =PropertyCopy.populateList(userList, dtoList, UserDto.class);
    	return dtoList;
    }
	@Override
	public List<UserDto> findAlljpaId(String id) {
		List<User> userList = repository.findbyId( id);
		List<UserDto> dtoList  =new ArrayList<UserDto>();
		dtoList =PropertyCopy.populateList(userList, dtoList, UserDto.class);
		return dtoList;
		}
	@Override
	public List<UserDto> findAlljdbc() {
		List<User> userList = userDao.findAll();
		List<UserDto> dtoList  =new ArrayList<UserDto>();
		dtoList =PropertyCopy.populateList(userList, dtoList, UserDto.class);
		return dtoList;
	}
	@Override
	public List<UserDto> findAlljpa3(String id) {
		return repository.finddtobyId( id);
	}
}