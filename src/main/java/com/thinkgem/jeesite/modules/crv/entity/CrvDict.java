/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 数据字典Entity
 * @author fanxiaowei
 * @version 2017-04-27
 */
public class CrvDict extends DataEntity<CrvDict> {
	
	private static final long serialVersionUID = 1L;
	private String value;		// value
	private String label;		// label
	private String type;		// type
	private String description;		// description
	private String sort;		// sort
	private CrvDict parent;		// parent_id
	
	public CrvDict() {
		super();
	}

	public CrvDict(String id){
		super(id);
	}

	@Length(min=1, max=100, message="value长度必须介于 1 和 100 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=1, max=100, message="label长度必须介于 1 和 100 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Length(min=1, max=100, message="type长度必须介于 1 和 100 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=100, message="description长度必须介于 1 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@JsonBackReference
	public CrvDict getParent() {
		return parent;
	}

	public void setParent(CrvDict parent) {
		this.parent = parent;
	}
	
}