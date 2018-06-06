/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.crv.entity.CrvArea;
import com.thinkgem.jeesite.modules.crv.service.CrvAreaService;

/**
 * 区域管理Controller
 * 
 * @author fanxiaowei
 * @version 2017-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/crv/crvArea")
public class CrvAreaController extends BaseController {

	@Autowired
	private CrvAreaService crvAreaService;

	@ModelAttribute
	public CrvArea get(@RequestParam(required = false) String id) {
		CrvArea entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = crvAreaService.get(id);
		}
		if (entity == null) {
			entity = new CrvArea();
		}
		return entity;
	}

	@RequiresPermissions("crv:crvArea:view")
	@RequestMapping(value = { "list", "" })
	public String list(CrvArea crvArea, Model model) {
		model.addAttribute("list", crvAreaService.findAll());
		return "modules/crv/crvAreaList";
	}

	@RequiresPermissions("crv:crvArea:view")
	@RequestMapping(value = "form")
	public String form(CrvArea crvArea, Model model) {
		model.addAttribute("crvArea", crvArea);
		return "modules/crv/crvAreaForm";

	}

	@RequiresPermissions("crv:crvArea:edit")
	@RequestMapping(value = "save")
	public String save(CrvArea crvArea, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crvArea)) {
			return form(crvArea, model);
		}
		crvAreaService.save(crvArea);
		addMessage(redirectAttributes, "保存区域管理成功");
		return "redirect:" + Global.getAdminPath() + "/crv/crvArea/?repage";
	}

	@RequiresPermissions("crv:crvArea:edit")
	@RequestMapping(value = "delete")
	public String delete(CrvArea crvArea, RedirectAttributes redirectAttributes) {
		crvAreaService.delete(crvArea);
		addMessage(redirectAttributes, "删除区域管理成功");
		return "redirect:" + Global.getAdminPath() + "/crv/crvArea/?repage";
	}

}