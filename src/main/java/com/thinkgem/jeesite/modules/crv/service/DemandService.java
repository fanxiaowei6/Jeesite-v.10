/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.crv.entity.Demand;
import com.thinkgem.jeesite.modules.crv.dao.DemandDao;

/**
 * 需求分析Service
 * @author fanxiaowei
 * @version 2017-04-18
 */
@Service
@Transactional(readOnly = true)
public class DemandService extends CrudService<DemandDao, Demand> {

	public Demand get(String id) {
		return super.get(id);
	}
	
	public List<Demand> findList(Demand demand) {
		return super.findList(demand);
	}
	
	public Page<Demand> findPage(Page<Demand> page, Demand demand) {
		return super.findPage(page, demand);
	}
	
	@Transactional(readOnly = false)
	public void save(Demand demand) {
		super.save(demand);
	}
	
	@Transactional(readOnly = false)
	public void delete(Demand demand) {
		super.delete(demand);
	}
	
}