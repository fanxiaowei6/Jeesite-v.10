package com.thinkgem.jeesite.modules.hr.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hr.entity.Hr;
import com.thinkgem.jeesite.modules.hr.service.HrTaskService;
import com.thinkgem.jeesite.modules.hr.utils.HrUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
@Controller
@RequestMapping(value = "${adminPath}/hr/task")
public class HrTaskController extends BaseController {
	
	@Autowired HrTaskService hrTaskService;
	
	/**
	 * 新建任务
	 * 
	 */
	@RequiresPermissions("hr:task:edit")
	@RequestMapping(value="process")
	public String PorcessList(String category ,HttpServletRequest request,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes){
		Page<Object[]> page = new Page<Object[]>(request,response);
		page = hrTaskService.processList(page, category);
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "modules/hr/hrTaskProcessList";
	}
	
	/**
	 * 启动流程
	 * 
	 */
	@RequiresPermissions("hr:task:edit")
	@RequestMapping(value="form")
	public String form (Hr hr,HttpServletRequest request,Model model){

		// 获取流程XML上的表单KEY
		String formKey = hrTaskService.getFormKey(hr.getProcDefId(), hr.getTaskDefKey());
		if(hr.getProcInsId() != null){
			hr.setProcIns(hrTaskService.getProcIns(hr.getProcInsId()));
		}
		return "redirect:" + HrUtils.getFormUrl(formKey, hr);
	}

	/**
	 * 待办流程
	 * 
	 */
	@RequiresPermissions("hr:task:edit")
	@RequestMapping(value={"todo",""})
	public String todoList(Hr hr,HttpServletResponse response,Model model){
		List<Hr> list = hrTaskService.todoList(hr);
		model.addAttribute("list", list);
		if(UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, list);
		}
		return "modules/hr/hrTaskTodoList";
	}
	/**
	 * 已办流程
	 * 
	 */
	@RequiresPermissions(value="hr:task:edit")
	@RequestMapping(value="historic")
	public String historic(Hr hr,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<Hr> page=new Page<Hr>(request,response);
		page = hrTaskService.historicList(page, hr);
		model.addAttribute("page", page);
		if(UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, page);
		}
		return "modules/hr/hrTaskHistoricList";
		
	}
	
	
}
