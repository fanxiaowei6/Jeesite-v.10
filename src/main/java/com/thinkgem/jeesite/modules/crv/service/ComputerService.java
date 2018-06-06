/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.crv.entity.Computer;
import com.thinkgem.jeesite.modules.crv.dao.ComputerDao;


/**
 * 环境管理Service
 * @author fanxiaowei
 * @version 2017-04-18
 */
@Service
@Transactional(readOnly = true)
public class ComputerService extends CrudService<ComputerDao, Computer> {

	@Autowired
	private ComputerDao computerDao;
	public Computer get(String id) {
		return super.get(id);
	}
	
	public List<Computer> findList(Computer computer) {
		return super.findList(computer);
	}
	
	public Page<Computer> findComputer(Page<Computer> page, Computer computer) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		//computer.getSqlMap().put("dsf", dataScopeFilter(computer.getCurrentUser(), "o", "a"));
		// 设置分页参数
		computer.setPage(page);
		// 执行分页查询
		page.setList(computerDao.findList(computer));
		return page;
	}
	
	public Page<Computer> findPage(Page<Computer> page, Computer computer) {
		return super.findPage(page, computer);
	}
	
	@Transactional(readOnly = false)
	public void save(Computer computer) {
		super.save(computer);
	}
	
	@Transactional(readOnly = false)
	public void delete(Computer computer) {
		super.delete(computer);
	}
	
	
}