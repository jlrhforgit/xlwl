package com.jlrh.heagle.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlrh.heagle.server.domain.User;
import com.jlrh.heagle.server.dto.UserDTO;
import com.jlrh.heagle.server.jdbctemplate.UserDao;
import com.jlrh.heagle.server.mybatismapper.UserMapper;
import com.jlrh.heagle.server.repository.UserRepository;
import com.jlrh.heagle.server.service.UserService;
import com.jlrh.heagle.server.utils.PropertyCopy;

@Service("userService")
public class UserServiceimpl implements UserService  {
	
	private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired 
    private UserRepository repository; 
    @Autowired 
    private UserDao userDao; 
    
    @Override
    public List<UserDTO> findAll() {
    	List<User> userList = userMapper.findAll();
    	List<UserDTO> dtoList  =new ArrayList<UserDTO>();
    	log.info("复制实体类属性到dto开始。。。");
    	PropertyCopy.populateList(userList, dtoList, UserDTO.class);
        return dtoList;
    }
    @Override
    public List<UserDTO> findAlljpa() {
    	List<User> userList = repository.findAll();
    	List<UserDTO> dtoList  =new ArrayList<UserDTO>();
    	dtoList =PropertyCopy.populateList(userList, dtoList, UserDTO.class);
    	return dtoList;
    }
	@Override
	public List<UserDTO> findAlljpaId(String id) {
		List<User> userList = repository.findbyId( id);
		List<UserDTO> dtoList  =new ArrayList<UserDTO>();
		dtoList =PropertyCopy.populateList(userList, dtoList, UserDTO.class);
		return dtoList;
		}
	@Override
	public List<UserDTO> findAlljdbc() {
		List<User> userList = userDao.findAll();
		List<UserDTO> dtoList  =new ArrayList<UserDTO>();
		dtoList =PropertyCopy.populateList(userList, dtoList, UserDTO.class);
		return dtoList;
	}
	@Override
	public List<UserDTO> findAlljpa3(String id) {
		
		List<User> userList = repository.finddtobyId( id);
		List<UserDTO> dtoList  =new ArrayList<UserDTO>();
		dtoList =PropertyCopy.populateList(userList, dtoList, UserDTO.class);
		return dtoList;
	}
//	@Override
//	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		  //查数据库
////        User user = userMapper.loadUserByUsername( userName );
//		UserDTO user = repository.getbyName(userName);
//        if (null != user) {
//            List<URoleDTO> roles = roleDao.getRolesByUserId( user.getId() );
//            user.setAuthoritie( roles );
//        }
//
//        return user;
//	}
}