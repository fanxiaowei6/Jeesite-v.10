/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yaer.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 年产值报表Entity
 * @author fanxiaowei
 * @version 2017-12-26
 */
public class Production extends DataEntity<Production> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 搜索引擎
	private String value;		// 访问量
	
	public Production() {
		super();
	}

	public Production(String id){
		super(id);
	}

	@Length(min=0, max=64, message="搜索引擎长度必须介于 0 和 64 之间")
	@ExcelField(title="产品", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=11, message="访问量长度必须介于 0 和 11 之间")
	@ExcelField(title="收益", align=2, sort=10)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}