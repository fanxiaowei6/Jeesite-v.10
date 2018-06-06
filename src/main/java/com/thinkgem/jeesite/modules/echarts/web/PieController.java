package com.thinkgem.jeesite.modules.echarts.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.MyBeanUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.echarts.Entity.Pie;
import com.thinkgem.jeesite.modules.echarts.service.PieService;
import com.thinkgem.jeesite.modules.yaer.entity.Production;

@Controller
@RequestMapping(value = "${adminPath}/echarts/pie")
public class PieController extends BaseController {
	
	@Autowired
	private PieService pieService;
	
	
	


	public Pie get(@RequestParam(required=false)String id){
		Pie entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pieService.get(id);
		}
		if (entity == null){
			entity = new Pie();
		}
		return entity;
	}
	
	//显示报表统计
	@RequiresPermissions("echarts:pie:list")
	@RequestMapping(value={"list",""})
	public String list(Pie pie1,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<Pie> page = pieService.findPage(new Page<Pie>(request,response), pie1);
		model.addAttribute("page", page);
		//生成报表数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		List<Pie> list = pieService.findList(pie1);
		for (Pie pie : list) {
			orientData.put(pie.getName(), pie.getValue());
		}
		model.addAttribute("orientData", orientData);
		return "modules/echarts/pieList";
	}
	
	
	/**
	 * 查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"echarts:pie:view","echarts:pie:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Pie pie, Model model) {
		model.addAttribute("pie", pie);
		return "modules/echarts/pieForm";
	}

	/**
	 * 保存
	 */
	@RequiresPermissions(value={"echarts:pie:add","echarts:pie:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Pie pie, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, pie)){
			return form(pie, model);
		}
		if(!pie.getIsNewRecord()){//编辑表单保存
			Pie t = pieService.get(pie.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(pie, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			pieService.save(t);//保存
		}else{//新增表单保存
			pieService.save(pie);//保存
		}
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/pie/?repage";
	}
	/**
	 * 删除
	 */
	@RequiresPermissions(value={"echarts:pie:del"})
	@RequestMapping(value="delete")
	public String delete(Pie pie ,RedirectAttributes redirectAttributes){
		pieService.delete(pie);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/pie/?repage";
		
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("echarts:pie:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			pieService.delete(pieService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/pie/?repage";
	}

	@RequiresPermissions("echarts:pie:export")
	@RequestMapping(value ="export",method=RequestMethod.POST)
	public String export(Pie pie,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
		
		try {
			String fileName="搜索引擎"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Pie> page =pieService.findPage(new Page<Pie>(request, response, -1), pie);
				new ExportExcel("搜索引擎", Pie.class).setDataList(page.getList()).write(response, fileName).dispose();
			//return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "导出记录失败，失败信息是："+e.getMessage());
			
		}
		
		return "redirect:"+Global.getAdminPath()+"/echarts/pie/?repage";
	}
	
}
