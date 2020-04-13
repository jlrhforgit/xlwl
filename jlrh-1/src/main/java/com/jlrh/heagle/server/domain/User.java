package com.jlrh.heagle.server.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jlrh.heagle.domain.DomainImpl;


@Entity  
@Table(name = "tb_user")
public class User extends  DomainImpl  {
	 /**
	 * 
	 */
	    @Id 
	    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	    private Long id;//编号
	    @Column 
	    private String username;//用户名
	    @Column 
	    private String password;//密码
	    //省略get set方法
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	    
	     
	    
}
