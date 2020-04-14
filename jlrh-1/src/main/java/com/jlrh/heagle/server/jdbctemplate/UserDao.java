package com.jlrh.heagle.server.jdbctemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jlrh.heagle.server.domain.User;
import com.jlrh.heagle.server.utils.MapUtils;

@Repository
public class UserDao {
	@Autowired
	JdbcTemplate jdbc;
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public List<User>  findAll() {
		String sql ="select * from tb_user";
		log.info("jdbctemplate执行sql:"+sql);
		List< Map<String,Object>> m = jdbc.queryForList(sql);
		List<User> l = new ArrayList<User>();
		for(int i=0 ;i<m.size();i++) {
			User u =null;
			try {
				u = MapUtils.mapToObject( m.get(i),User.class);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			l.add(u);
		}
		return l;
		
	}


}
