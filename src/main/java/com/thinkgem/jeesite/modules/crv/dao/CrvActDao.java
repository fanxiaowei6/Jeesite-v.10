package com.thinkgem.jeesite.modules.crv.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import com.thinkgem.jeesite.modules.crv.entity.CrvAct;
@MyBatisDao
public interface CrvActDao extends CrudDao<CrvAct> {
	public int updateProcInsIdByBusinessId(CrvAct crvAct);

}
