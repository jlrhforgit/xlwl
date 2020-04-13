package com.jlrh.heagle.server.mybatismapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jlrh.heagle.server.domain.User;

@Mapper//指定这是一个操作数据库的mapper
public interface UserMapper {
	 List<User> findAll();
}

