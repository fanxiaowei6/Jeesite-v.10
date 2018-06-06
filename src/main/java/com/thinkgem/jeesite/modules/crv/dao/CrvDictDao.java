/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.crv.entity.CrvDict;

/**
 * 数据字典DAO接口
 * @author fanxiaowei
 * @version 2017-04-27
 */
@MyBatisDao
public interface CrvDictDao extends CrudDao<CrvDict> {
	
	public List<String> findTypeList(CrvDict crvDict);
}