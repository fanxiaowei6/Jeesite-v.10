/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.crv.entity.Demand;
import com.thinkgem.jeesite.modules.crv.service.DemandService;

/**
 * 需求分析Controller
 * @author fanxiaowei
 * @version 2017-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/crv/demand")
public class DemandController extends BaseController {

	@Autowired
	private DemandService demandService;
	
	@ModelAttribute
	public Demand get(@RequestParam(required=false) String id) {
		Demand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = demandService.get(id);
		}
		if (entity == null){
			entity = new Demand();
		}
		return entity;
	}
	
	@RequiresPermissions("crv:demand:view")
	@RequestMapping(value = {"list", ""})
	public String list(Demand demand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Demand> page = demandService.findPage(new Page<Demand>(request, response), demand); 
		model.addAttribute("page", page);
		return "modules/crv/demandList";
	}

	@RequiresPermissions("crv:demand:view")
	@RequestMapping(value = "form")
	public String form(Demand demand, Model model) {
		model.addAttribute("demand", demand);
		return "modules/crv/demandForm";
	}

	@RequiresPermissions("crv:demand:edit")
	@RequestMapping(value = "save")
	public String save(Demand demand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, demand)){
			return form(demand, model);
		}
		demandService.save(demand);
		addMessage(redirectAttributes, "保存需求分析成功");
		return "redirect:"+Global.getAdminPath()+"/crv/demand/?repage";
	}
	
	@RequiresPermissions("crv:demand:edit")
	@RequestMapping(value = "delete")
	public String delete(Demand demand, RedirectAttributes redirectAttributes) {
		demandService.delete(demand);
		addMessage(redirectAttributes, "删除需求分析成功");
		return "redirect:"+Global.getAdminPath()+"/crv/demand/?repage";
	}

}