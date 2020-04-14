package com.jlrh.heagle.server.dto;

import java.util.List;


public class UserDTO  {
	/**
	 * 
	 */
	private Long id;// 编号
	private String username;// 用户名
	private String password;// 密码
	
	private List<URoleDTO> authoritie;

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


	public void setAuthoritie(List<URoleDTO> authoritie) {
		this.authoritie = authoritie;
	}
	
	public List<URoleDTO> getAuthoritie() {
		return  authoritie;
	}

	public UserDTO(Long id, String username, String password, List<URoleDTO> authoritie) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.authoritie = authoritie;
	}

	public UserDTO() {
		super();
	}


	 /**
     * 用户账号是否过期
     */
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
    /**
     * 用户账号是否被锁定
     */
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }

    /**
     * 用户密码是否过期
     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }

    /**
     * 用户是否可用
     */
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return (Collection<? extends GrantedAuthority>) authoritie;
//	}
}
