/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yaer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.yaer.entity.Production;

/**
 * 年产值报表DAO接口
 * @author fanxiaowei
 * @version 2017-12-26
 */
@MyBatisDao
public interface ProductionDao extends CrudDao<Production> {
	
}