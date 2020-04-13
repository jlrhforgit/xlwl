package com.jlrh.heagle.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.jlrh.heagle.domain.DomainImpl;

@Entity
public class UUserRole  extends  DomainImpl   {
	 /**{@link UUser.id}*/
	 @Column 
    private Long uid;
    /**{@link URole.id}*/
	 @Column 
    private Long rid;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public UUserRole(Long uid, Long rid) {
		super();
		this.uid = uid;
		this.rid = rid;
	}
	public UUserRole() {
		super();
	}
	 
}
