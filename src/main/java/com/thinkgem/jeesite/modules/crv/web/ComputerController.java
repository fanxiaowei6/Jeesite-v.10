/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crv.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.crv.entity.Computer;
import com.thinkgem.jeesite.modules.crv.service.ComputerService;

/**
 * 环境管理Controller
 * @author fanxiaowei
 * @version 2017-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/crv/computer")
public class ComputerController extends BaseController {

	@Autowired
	private ComputerService computerService;
	
	@ModelAttribute
	public Computer get(@RequestParam(required=false) String id) {
		Computer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = computerService.get(id);
		}
		if (entity == null){
			entity = new Computer();
		}
		return entity;
	}
	/**
	 * 导出IT设备数据
	 * @param computer
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("crv:computer:view")
	@RequestMapping(value= "export", method=RequestMethod.POST)
	public String exportFile(Computer computer,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		
		
		try {
			String fileName="IT设备数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Computer> page = computerService.findComputer(new Page<Computer>(request, response, -1), computer);
			new ExportExcel("IT设备数据", Computer.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes,"导出数据失败，失败消息是："+e.getMessage());
		}
		return "redirect"+adminPath+"/crv/computer/list?repage";
	}
	/**
	 * 导入excel IT设备数据
	 * @param computer
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("crv:computer:edit")
	@RequestMapping(value="import",method=RequestMethod.POST)
	public String importFile(MultipartFile file ,RedirectAttributes redirectAttributes){
		
		
			try {
				int successNum = 0;
				int failureNum = 0;
				StringBuilder failureMsg = new StringBuilder();
				ImportExcel ei = new ImportExcel(file, 1, 0);
				List<Computer> list = ei.getDataList(Computer.class);
				for (Computer computer : list) {
					if(null!=computer){
						computerService.save(computer);
						successNum++;
					}
					else{
						failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
						failureNum++;
					}
						
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		return "redirect:"+adminPath+"/crv/computer/list?repage";
	}
	
	
	
	
	@RequiresPermissions("crv:computer:view")
	@RequestMapping(value = {"list", ""})
	public String list(Computer computer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Computer> page = computerService.findPage(new Page<Computer>(request, response), computer); 
		model.addAttribute("page", page);
		return "modules/crv/computerList";
	}

	@RequiresPermissions("crv:computer:view")
	@RequestMapping(value = "form")
	public String form(Computer computer, Model model) {
		model.addAttribute("computer", computer);
		return "modules/crv/computerForm";
	}

	@RequiresPermissions("crv:computer:edit")
	@RequestMapping(value = "save")
	public String save(Computer computer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, computer)){
			return form(computer, model);
		}
		computerService.save(computer);
		addMessage(redirectAttributes, "保存环境管理成功");
		return "redirect:"+adminPath+"/crv/computer/?repage";
	}
	
	@RequiresPermissions("crv:computer:edit")
	@RequestMapping(value = "delete")
	public String delete(Computer computer, RedirectAttributes redirectAttributes) {
		computerService.delete(computer);
		addMessage(redirectAttributes, "删除环境管理成功");
		return "redirect:"+adminPath+"/crv/computer/?repage";
	}

}