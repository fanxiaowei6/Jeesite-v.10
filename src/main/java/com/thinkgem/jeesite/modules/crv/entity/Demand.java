/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 需求分析Entity
 * @author fanxiaowei
 * @version 2017-04-18
 */
public class Demand extends DataEntity<Demand> {
	
	private static final long serialVersionUID = 1L;
	private String deName;		// de_name
	private String deType;		// de_type
	private String deDetail;		// de_detail
	
	public Demand() {
		super();
	}

	public Demand(String id){
		super(id);
	}

	@Length(min=0, max=20, message="de_name长度必须介于 0 和 20 之间")
	public String getDeName() {
		return deName;
	}

	public void setDeName(String deName) {
		this.deName = deName;
	}
	
	@Length(min=0, max=20, message="de_type长度必须介于 0 和 20 之间")
	public String getDeType() {
		return deType;
	}

	public void setDeType(String deType) {
		this.deType = deType;
	}
	
	@Length(min=0, max=255, message="de_detail长度必须介于 0 和 255 之间")
	public String getDeDetail() {
		return deDetail;
	}

	public void setDeDetail(String deDetail) {
		this.deDetail = deDetail;
	}
	
}