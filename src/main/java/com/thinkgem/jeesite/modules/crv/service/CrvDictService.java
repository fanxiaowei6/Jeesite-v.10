/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.crv.entity.CrvDict;
import com.thinkgem.jeesite.modules.crv.utils.CrvDictUtils;
import com.thinkgem.jeesite.modules.crv.dao.CrvDictDao;

/**
 * 数据字典Service
 * @author fanxiaowei
 * @version 2017-04-27
 */
@Service
@Transactional(readOnly = true)
public class CrvDictService extends CrudService<CrvDictDao, CrvDict> {

	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new CrvDict());
	}
	public CrvDict get(String id) {
		return super.get(id);
	}
	
	public List<CrvDict> findList(CrvDict crvDict) {
		return super.findList(crvDict);
	}
	
	public Page<CrvDict> findPage(Page<CrvDict> page, CrvDict crvDict) {
		return super.findPage(page, crvDict);
	}
	
	@Transactional(readOnly = false)
	public void save(CrvDict crvDict) {
		super.save(crvDict);
		CacheUtils.remove(CrvDictUtils.CACHE_CRVDICT_MAP);
	}
	
	@Transactional(readOnly = false)
	public void delete(CrvDict crvDict) {
		super.delete(crvDict);
		CacheUtils.remove(CrvDictUtils.CACHE_CRVDICT_MAP);
	}
	
}