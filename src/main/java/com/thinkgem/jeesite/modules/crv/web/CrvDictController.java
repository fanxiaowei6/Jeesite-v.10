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
import com.thinkgem.jeesite.modules.crv.entity.CrvDict;
import com.thinkgem.jeesite.modules.crv.service.CrvDictService;

/**
 * 数据字典Controller
 * @author fanxiaowei
 * @version 2017-04-27
 */
@Controller
@RequestMapping(value = "${adminPath}/crv/crvDict")
public class CrvDictController extends BaseController {

	@Autowired
	private CrvDictService crvDictService;
	
	@ModelAttribute
	public CrvDict get(@RequestParam(required=false) String id) {
		CrvDict entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = crvDictService.get(id);
		}
		if (entity == null){
			entity = new CrvDict();
		}
		return entity;
	}
	
	@RequiresPermissions("crv:crvDict:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrvDict crvDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrvDict> page = crvDictService.findPage(new Page<CrvDict>(request, response), crvDict); 
		model.addAttribute("page", page);
		return "modules/crv/crvDictList";
	}

	@RequiresPermissions("crv:crvDict:view")
	@RequestMapping(value = "form")
	public String form(CrvDict crvDict, Model model) {
		model.addAttribute("crvDict", crvDict);
		return "modules/crv/crvDictForm";
	}

	@RequiresPermissions("crv:crvDict:edit")
	@RequestMapping(value = "save")
	public String save(CrvDict crvDict, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crvDict)){
			return form(crvDict, model);
		}
		crvDictService.save(crvDict);
		addMessage(redirectAttributes, "保存数据字典成功");
		return "redirect:"+Global.getAdminPath()+"/crv/crvDict/?repage";
	}
	
	@RequiresPermissions("crv:crvDict:edit")
	@RequestMapping(value = "delete")
	public String delete(CrvDict crvDict, RedirectAttributes redirectAttributes) {
		crvDictService.delete(crvDict);
		addMessage(redirectAttributes, "删除数据字典成功");
		return "redirect:"+Global.getAdminPath()+"/crv/crvDict/?repage";
	}

}