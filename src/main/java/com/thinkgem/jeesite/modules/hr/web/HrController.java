package com.thinkgem.jeesite.modules.hr.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hr.service.HrModelService;

@Controller
@RequestMapping(value="${adminPath}/hr/model")
public class HrController extends BaseController {

	@Autowired
	private HrModelService hrModelService;
	
	/**
	 * 模型列表
	 */
	@RequiresPermissions("hr:model:edit")
	@RequestMapping(value={"list",""})
	public String modelList(String category,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<org.activiti.engine.repository.Model> page = hrModelService.modelList(new Page<org.activiti.engine.repository.Model>(request, response), category);
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "modules/hr/hrModelList";
	}
	/**
	 * 创建模型
	 */
	@RequiresPermissions("hr:model:edit")
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(String id){
		return "modules/hr/hrModelCreate";
	}
	
	/**
	 * cjmx
	 */
	@RequiresPermissions("hr:model:edit")
	@RequestMapping(value="create",method=RequestMethod.POST)
	public void create(String name, String key, String description,String category,HttpServletRequest request,HttpServletResponse response){
		try {
			org.activiti.engine.repository.Model modelData = hrModelService.create(name, key, description, category);
			response.sendRedirect(request.getContextPath() + "/hr/process-editor/modeler.jsp?modelId=" + modelData.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("创建模型失败"+e);
		}
	}
	/**
	 * 根据model部署
	 */
	@RequiresPermissions("hr:model:edit")
	@RequestMapping(value="deploy")
	public String deploy (String id,RedirectAttributes redirectAttributes){
		String message=hrModelService.deploy(id);
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:" + adminPath + "hr/process";
	}
}
