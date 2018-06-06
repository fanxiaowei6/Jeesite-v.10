/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.crv.entity.CrvArea;
import com.thinkgem.jeesite.modules.crv.dao.CrvAreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 区域管理Service
 * @author fanxiaowei
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = true)
public class CrvAreaService extends TreeService<CrvAreaDao, CrvArea> {

	public List<CrvArea> findAll(){
		return UserUtils.getCrvAreaList();
	}
	public CrvArea get(String id) {
		return super.get(id);
	}
	
	public List<CrvArea> findList(CrvArea crvArea) {
		return super.findList(crvArea);
	}
	
	public Page<CrvArea> findPage(Page<CrvArea> page, CrvArea crvArea) {
		return super.findPage(page, crvArea);
	}
	
	@Transactional(readOnly = false)
	public void save(CrvArea crvArea) {
		super.save(crvArea);
		UserUtils.removeCache(UserUtils.CACHE_CRVAREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(CrvArea crvArea) {
		super.delete(crvArea);
		UserUtils.removeCache(UserUtils.CACHE_CRVAREA_LIST);
	}
	
}