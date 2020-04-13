package com.jlrh.heagle.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.jlrh.heagle.domain.DomainImpl;

/**
 * 
 * 开发公司：itboy.net<br/>
 * 版权：itboy.net<br/>
 * <p>
 * 
 * 角色{@link URole}和 权限{@link UPermission}中间表
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　周柏成　2016年5月25日 　<br/>
 * <p>
 * *******
 * <p>
 * @author zhou-baicheng
 * @email  i@itboy.net
 * @version 1.0,2016年5月25日 <br/>
 * 
 */
@Entity
public class URolePermission extends DomainImpl{
	/**{@link URole.id}*/
	@Column
    private Long rid;
    /**{@link UPermission.id}*/
	@Column
    private Long pid;

    public URolePermission() {
	}
    public URolePermission(Long rid,Long pid) {
    	this.rid = rid;
    	this.pid = pid;
    }
    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}