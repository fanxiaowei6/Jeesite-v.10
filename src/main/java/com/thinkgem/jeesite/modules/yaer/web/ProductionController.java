/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yaer.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.yaer.entity.Production;
import com.thinkgem.jeesite.modules.yaer.service.ProductionService;

/**
 * 年产值报表Controller
 * @author fanxiaowei
 * @version 2017-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/yaer/production")
public class ProductionController extends BaseController {

	@Autowired
	private ProductionService productionService;
	
	@ModelAttribute
	public Production get(@RequestParam(required=false) String id) {
		Production entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productionService.get(id);
		}
		if (entity == null){
			entity = new Production();
		}
		return entity;
	}

	
	@RequiresPermissions("yaer:production:view")
	@RequestMapping(value = {"list", ""})
	public String list(Production production1, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Production> page = productionService.findPage(new Page<Production>(request, response), production1); 
		model.addAttribute("page", page);
		//生成报表数据
		Map<String,Object> orientData = new HashMap<String, Object>();
		List<Production> list = productionService.findList(production1);
		for (Production production : list) {
			orientData.put(production.getName(), production.getValue());
			
		}
		model.addAttribute("orientData", orientData);
		return "modules/yaer/productionList";
	}
	

	@RequiresPermissions("yaer:production:view")
	@RequestMapping(value = "form")
	public String form(Production production, Model model) {
		model.addAttribute("production", production);
		return "modules/yaer/productionForm";
	}

	@RequiresPermissions("yaer:production:edit")
	@RequestMapping(value = "save")
	public String save(Production production, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, production)){
			return form(production, model);
		}
		productionService.save(production);
		addMessage(redirectAttributes, "保存年产值报表成功");
		return "redirect:"+Global.getAdminPath()+"/yaer/production/?repage";
	}
	
	@RequiresPermissions("yaer:production:edit")
	@RequestMapping(value = "delete")
	public String delete(Production production, RedirectAttributes redirectAttributes) {
		productionService.delete(production);
		addMessage(redirectAttributes, "删除年产值报表成功");
		return "redirect:"+Global.getAdminPath()+"/yaer/production/?repage";
	}
	@RequiresPermissions("yaer:production:edit")
	@RequestMapping(value ="deleteAll")
	public String deleteAll(String ids,RedirectAttributes redirectAttributes){
		String idArray[] =ids.split(",");
		for (String id : idArray) {
			productionService.delete(productionService.get(id));
		}
		
		return "redirect:"+Global.getAdminPath()+"/yaer/production/?repage";
	}
	/**
	 * 导出excel
	 * @param production
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("yaer:production:export")
	@RequestMapping(value ="export",method=RequestMethod.POST)
	public String export(Production production,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
		
		try {
			String fileName="年产值报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Production> page =productionService.findPage(new Page<Production>(request, response, -1), production);
		
				new ExportExcel("年产值报表", Production.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "导出记录失败，失败信息是："+e.getMessage());
			
		}
		
		return "redirect:"+Global.getAdminPath()+"/yaer/production/?repage";
	}
	/**
	 * 导入excel
	 */
	 @RequiresPermissions("yaer:production:import")
	 @RequestMapping(value="import",method=RequestMethod.POST)
	 public String importFile(MultipartFile file ,RedirectAttributes redirectAttributes){
		 try {
			int successNum=0;
			 int failureNum=0;
			 StringBuilder failureMsg=new StringBuilder();
			 ImportExcel ei=new ImportExcel(file, 1, 0);
			 List<Production> list = ei.getDataList(Production.class);
			 
			 for (Production production : list) {
				try {
					productionService.save(production);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					failureNum++;
				}
				if(failureNum>0){
					failureMsg.insert(0,"失败"+failureNum+"条记录！");
				}
				addMessage(redirectAttributes, "已成功导入"+successNum+"记录");
				 
			}
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "导入失败，失败信息："+e.getMessage());
		}
		 return "redirect:"+Global.getAdminPath()+"/yaer/production/?repage";
	 }
	 /**
	  * 下载模板
	  */
	@RequiresPermissions("yaer:production:import")
	@RequestMapping(value="import/template")
	public String importFileTemplate(HttpServletResponse response ,RedirectAttributes redirectAttributes){
		
		try {
			String fileName="导入模板.xlsx";
			List<Production> list = Lists.newArrayList();
			new ExportExcel("导入模板",Production.class,1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/yaer/production/?repage";
	}
	 

}