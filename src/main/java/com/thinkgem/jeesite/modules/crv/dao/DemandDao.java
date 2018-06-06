/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.crv.entity.Demand;

/**
 * 需求分析DAO接口
 * @author fanxiaowei
 * @version 2017-04-18
 */
@MyBatisDao
public interface DemandDao extends CrudDao<Demand> {
	
}