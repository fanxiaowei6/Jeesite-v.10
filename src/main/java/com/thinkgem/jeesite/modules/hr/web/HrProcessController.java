package com.thinkgem.jeesite.modules.hr.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hr.service.HrProcessService;
@Controller
@RequestMapping(value="${adminPath}/hr/process")
public class HrProcessController extends BaseController {
	
	@Autowired
	private HrProcessService hrProcessService;
	/**
	 * 流程定义列表
	 * @param category
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("hr:process:edit")
	@RequestMapping(value={"list",""})
	public String PorcessList(String category,HttpServletRequest request,HttpServletResponse response,Model model){
		 Page<Object[]> page = hrProcessService.processList(new Page<Object[]>(request, response), category);
			model.addAttribute("page", page);
			model.addAttribute("category", category);
			return "modules/hr/hrProcessList";
	}
	/**
	 * 
	 * 激活、挂起流程实例
	 * @param state
	 * @return
	 */
	@RequiresPermissions("hr:process:edit")
	@RequestMapping(value={"update/{state}"})
	public String updateState(@PathVariable("state")String state,String procDefId,RedirectAttributes redirectAttributes){
		String message =hrProcessService.updateState(state, procDefId);
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:" + adminPath+ "/hr/process";
		
	}
	/**
	 * 删除部署流程
	 * 
	 */
	@RequiresPermissions("hr:process:edit")
	@RequestMapping(value="delete")
	public String delete(String deploymentId){
		hrProcessService.deleteDeployment(deploymentId);
		return "redirect:" + adminPath+ "/hr/process";
	}
	
	/**
	 * 转化为模型
	 * @throws XMLStreamException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@RequiresPermissions("hr:process:edit")
	@RequestMapping(value="convert/{toModel}")
	public String ConvertToModel(String procDefId,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, XMLStreamException{
		org.activiti.engine.repository.Model model =hrProcessService.convertToModel(procDefId);
		redirectAttributes.addFlashAttribute("message", "模型转换成功，模型ID："+model.getId());
		return "redirect:" + adminPath+ "/hr/model";
	}
	/**
	 * 部署流程
	 */
	@RequiresPermissions("hr:process:edit")
	@RequestMapping(value = "/deploy", method=RequestMethod.GET)
	public String deploy(Model model) {
		return "modules/hr/hrProcessDeploy";
	}
	
	/**
	 * 部署流程-保存
	 * 
	 */
	@RequiresPermissions("hr:process:edit")
	@RequestMapping(value="/deploy",method=RequestMethod.POST)
	public String deploy(String exportDir, String category, MultipartFile file,RedirectAttributes redirectAttributes){
		String fileName=file.getOriginalFilename();
		if(StringUtils.isBlank(fileName)){
			redirectAttributes.addFlashAttribute("message", "请选择要部署的文件");
		}
		else{
			String message=hrProcessService.deploy(exportDir, category, file);
			redirectAttributes.addFlashAttribute("message", message);
		}
		return "redirect:" + adminPath +"/hr/process";
	}
	/**
	 * 运行中的列表
	 * 
	 */
	@RequiresPermissions("hr:process:edit")
	@RequestMapping(value="running")
	public String RunningList(String procInsId, String procDefKey,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<ProcessInstance> page = hrProcessService.runningList(new Page<ProcessInstance>(request,response), procInsId, procDefKey);
		model.addAttribute("page", page);
		model.addAttribute("procDefKey", procDefKey);
		model.addAttribute("procInsId", procInsId);
		return "modules/hr/hrProcessRunningList";
	}
}
