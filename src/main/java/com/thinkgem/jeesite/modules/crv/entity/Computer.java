/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 环境管理Entity
 * @author fanxiaowei
 * @version 2017-04-18
 */
public class Computer extends DataEntity<Computer> {
	
	private static final long serialVersionUID = 1L;
	private User user;//归属用户
	private Office office;//归属部门
	private String compId;		// comp_id
	private String compName;		// comp_name
	private String compType;		// comp_type
	private String compDescribe;		// comp_describe
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Computer() {
		super();
	}

	public Computer(String id){
		super(id);
	}
	
	@Length(min=1, max=11, message="comp_id长度必须介于 1 和 11 之间")
	@ExcelField(title="编号",type=0, align=2, sort=1)
	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}
	
	@Length(min=0, max=10, message="comp_name长度必须介于 0 和 10 之间")
	@ExcelField(title="名称", align=2, sort=20)
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	@Length(min=0, max=20, message="comp_type长度必须介于 0 和 20 之间")
	@ExcelField(title="类型", align=2, sort=30)
	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}
	
	@Length(min=0, max=20, message="comp_describe长度必须介于 0 和 20 之间")
	@ExcelField(title="描述", align=2, sort=40)
	public String getCompDescribe() {
		return compDescribe;
	}

	public void setCompDescribe(String compDescribe) {
		this.compDescribe = compDescribe;
	}
	
}