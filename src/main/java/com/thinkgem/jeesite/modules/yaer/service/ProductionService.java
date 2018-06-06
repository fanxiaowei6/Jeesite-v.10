/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yaer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.yaer.entity.Production;
import com.thinkgem.jeesite.modules.yaer.dao.ProductionDao;

/**
 * 年产值报表Service
 * @author fanxiaowei
 * @version 2017-12-26
 */
@Service
@Transactional(readOnly = true)
public class ProductionService extends CrudService<ProductionDao, Production> {

	@Autowired
	private ProductionDao productionDao;
	
	public Production get(String id) {
		return super.get(id);
	}
	
	public List<Production> findList(Production production) {
		return super.findList(production);
	}
	
	public Page<Production> findPage(Page<Production> page, Production production) {
		
		return super.findPage(page, production);
	}
	
	@Transactional(readOnly = false)
	public void save(Production production) {
		super.save(production);
	}
	
	@Transactional(readOnly = false)
	public void delete(Production production) {
		super.delete(production);
	}
	
}