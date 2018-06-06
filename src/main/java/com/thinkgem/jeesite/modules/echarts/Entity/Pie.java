package com.thinkgem.jeesite.modules.echarts.Entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;


public class Pie extends DataEntity<Pie>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	
	public Pie() {
		super();
	}

	public Pie(String id){
		super(id);
	}

	
	@Length(min=0, max=64, message="长度必须介于 0 和 64 之间")
	@ExcelField(title="搜索引擎", align=2, sort=7)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(title="数量", align=2, sort=8)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	

	

}
