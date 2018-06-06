package com.thinkgem.jeesite.modules.crv.web;


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
import com.thinkgem.jeesite.modules.act.service.ActModelService;
import com.thinkgem.jeesite.modules.crv.service.CrvActService;

@Controller
@RequestMapping(value = "${adminPath}/crv/model")
public class CrvActController extends BaseController {

	@Autowired
	private CrvActService crvActService;

	/**
	 * 流程模型列表
	 */
	@RequiresPermissions("crv:model:edit")
	@RequestMapping(value = { "list", "" })
	public String modelList(String category, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Page<org.activiti.engine.repository.Model> page = crvActService
				.modelList(new Page<org.activiti.engine.repository.Model>(
						request, response), category);

		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "modules/crv/crvModelList";
	}

	/**
	 * 创建模型
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("crv:model:edit")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {

		return "modules/crv/crvModelCreate";
	}

	/**
	 * 创建模型
	 */
	@RequiresPermissions("crv:model:edit")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public void create(String name, String key, String description,
			String category, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			org.activiti.engine.repository.Model modelData = crvActService
					.create(name, key, description, category);
			response.sendRedirect(request.getContextPath()
					+ "/act/process-editor/modeler.jsp?modelId="
					+ modelData.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("创建失败" + e);
		}

	}

	

	/**
	 * 根据model部署流程
	 */
	@RequiresPermissions("crv:model:edit")
	@RequestMapping(value = "deploy")
	public String deploy(String id, RedirectAttributes redirectAttributes) {
		String message = crvActService.deploy(id);
		redirectAttributes.addFlashAttribute(message);
		return "redirect:" + adminPath + "/crv/process";
		
	}

	/**
	 * 删除模型
	 * 
	 */
	@RequiresPermissions("crv:model:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		crvActService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除成功OKOKOKO，模型ID是："
				+ id);
		return "redirect:" + adminPath + "/crv/model";

	}

}
