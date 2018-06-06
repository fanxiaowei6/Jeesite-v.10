package com.thinkgem.jeesite.modules.hrAudit.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hrAudit.entity.HrAudit;
import com.thinkgem.jeesite.modules.hrAudit.service.HrAuditService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
@Controller
@RequestMapping(value="${adminPath}/hr/audit")
public class HrAuditController extends BaseController {

	@Autowired
	private HrAuditService hrAuditService;
	
	/**
	 * 申请
	 * 
	 */
	@RequiresPermissions("hr:audit:edit")
	@RequestMapping(value="form")
	public String form(HrAudit hrAudit,Model model){
		model.addAttribute("hrAudit", hrAudit);		
		return "modules/hr/hrAuditForm";
	}
	
	@RequiresPermissions("hr:audit:edit")
	@RequestMapping(value="list")
	public String list(HrAudit hrAudit,HttpServletRequest request,HttpServletResponse response,Model model){
		User user = UserUtils.getUser();
		if(!user.isAdmin()){
			hrAudit.setCreateBy(user);
		}
		Page<HrAudit> page=hrAuditService.findPage(new Page<HrAudit>(request,response), hrAudit);
		model.addAttribute("page", page);
		return "modules/hr/hrAuditList";
	}
}
