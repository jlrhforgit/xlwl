package com.jlrh.heagle.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jlrh.heagle.domain.DomainImpl;

/**
 * 
 * 开发公司：itboy.net<br/>
 * 版权：itboy.net<br/>
 * <p>
 * 
 * 权限角色
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
public class URole  extends DomainImpl{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    /**角色名称*/
    @Column
    private String name;
    /**角色类型*/
    @Column
    private String type;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }


	public void setType(String type) {
        this.type = type;
    }
}