package com.thinkgem.jeesite.common.persistence;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.hr.entity.Hr;

public abstract class HrEntity<T> extends DataEntity<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Hr hr;//流程任务对象
	
	public HrEntity() {
		super();
	}
	
	public HrEntity(String id) {
		super(id);
	}
	
	@JsonIgnore
	public Hr getHr() {
		if (hr == null){
			hr = new Hr();
		}
		return hr;
	}

	public void setHr(Hr hr) {
		this.hr = hr;
	}

	/**
	 * 获取流程实例ID
	 * @return
	 */
	public String getProcInsId() {
		return this.getHr().getProcInsId();
	}

	/**
	 * 设置流程实例ID
	 * @param procInsId
	 */
	public void setProcInsId(String procInsId) {
		this.getHr().setProcInsId(procInsId);
	}
}
