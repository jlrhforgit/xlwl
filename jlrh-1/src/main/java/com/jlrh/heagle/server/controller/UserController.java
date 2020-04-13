package com.jlrh.heagle.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlrh.heagle.config.SpringContextUtil;
import com.jlrh.heagle.server.dto.UserDto;
import com.jlrh.heagle.server.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
/**
 * 测试mybatie获取数据
 * @return
 */
    @RequestMapping("/findAll")
    public List<UserDto> findAll(){
    	long bTime =System.currentTimeMillis();
    	log.info("测试mybatie获取数据");
//    	 测试上下文获取成果    begin
    	ApplicationContext context =SpringContextUtil.getApplicationContext();
    	DataSource dataSource = (DataSource) context.getBean("dataSource");
		System.out.println(dataSource.getClass());
//    	 测试上下文获取成果    end
        log.info("/hello执行时间："+(System.currentTimeMillis()-bTime) +"ms");
        return userService.findAll();
    }
    /**
     * 测试jpa获取数据 -1
     * @return
     */
    @RequestMapping("/findAlljpa")
    public List<UserDto> findAlljpa(){
//    	long bTime =System.currentTimeMillis();
    	log.info("测试jpa获取数据 -1");
    	return userService.findAlljpa();
    }
    /**
     * 测试jpa获取数据 -2
     * @return
     */
    @RequestMapping("/findAlljpaId")
    public List<UserDto> findAlljpaId(HttpServletRequest req){
    	log.info("测试jpa获取数据 -2");
    	String id =req.getParameter("id");
    	
    	return userService.findAlljpaId(id);
    }
    /**
     * 测试jpa获取数据 -3
     * @return
     */
    @RequestMapping("/findAlljpa3")
    public List<UserDto> findAlljpa3(HttpServletRequest req){
    	log.info("测试jpa获取数据 -2");
    	String id =req.getParameter("id");
    	
    	return userService.findAlljpa3(id);
    }
    /**
     * 测试jdbctemplate获取数据 -1
     * @return
     */
    @RequestMapping("/findAlljdbc")
    public List<UserDto> findAlljdbc(HttpServletRequest req){
    	log.info("测试jdbctemplate获取数据 -1");
    	
    	return userService.findAlljdbc();
    }

}
