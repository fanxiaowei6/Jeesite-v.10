package com.thinkgem.jeesite.modules.crv.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.crv.entity.Computer;

/**
 * 环境管理DAO接口
 * @author fanxiaowei
 * @version 2017-04-18
 */
@MyBatisDao
public interface ComputerDao extends CrudDao<Computer> {
	
}