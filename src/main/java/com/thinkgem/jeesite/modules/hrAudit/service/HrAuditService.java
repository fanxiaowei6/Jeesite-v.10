package com.thinkgem.jeesite.modules.hrAudit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hrAudit.dao.HrAuditDao;
import com.thinkgem.jeesite.modules.hrAudit.entity.HrAudit;
@Service
@Transactional(readOnly=true)
public class HrAuditService extends CrudService<HrAuditDao, HrAudit> {

	public Page<HrAudit> findPage(Page<HrAudit> page, HrAudit hrAudit) {
		hrAudit.setPage(page);
		page.setList(dao.findList(hrAudit));
		return page;
	}
	
}
