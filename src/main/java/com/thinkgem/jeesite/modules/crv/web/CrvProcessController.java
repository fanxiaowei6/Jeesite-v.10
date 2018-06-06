package com.thinkgem.jeesite.modules.crv.web;

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
import com.thinkgem.jeesite.modules.crv.service.CrvProcessService;
@Controller
@RequestMapping(value = "${adminPath}/crv/process")
public class CrvProcessController extends BaseController {

	@Autowired
	private CrvProcessService crvProcessService;
	
	/**
	 * 流程定义列表
	 */
	@RequiresPermissions("crv:process:edit")
	@RequestMapping(value = {"list", ""})
	public String processList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
	    Page<Object[]> page = crvProcessService.processList(new Page<Object[]>(request, response), category);
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		System.out.println("---------------------流程管理-----------------------------");
		return "modules/crv/crvProcessList";
	}
	
	
	/**
	 * 删除流程
	 */
	@RequiresPermissions("crv:process:edit")
	@RequestMapping(value="delete")
	public String delete (String deploymentId,RedirectAttributes redirectAttributes){
		crvProcessService.delete(deploymentId);
		redirectAttributes.addFlashAttribute("message","流程ID是:"+deploymentId);
		return "redirect:" + adminPath + "/crv/process";
	}
}
