package com.thinkgem.jeesite.modules.hr.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.hr.entity.Hr;

@MyBatisDao
public interface HrDao extends CrudDao<Hr> {

	public int updateProcInsIdByBusinessId(Act act);
}
