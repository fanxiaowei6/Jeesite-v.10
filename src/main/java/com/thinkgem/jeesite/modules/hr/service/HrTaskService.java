package com.thinkgem.jeesite.modules.hr.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;

import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefCache;
import com.thinkgem.jeesite.modules.hr.dao.HrDao;
import com.thinkgem.jeesite.modules.hr.entity.Hr;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
@Service
@Transactional(readOnly=true)
public class HrTaskService extends BaseService {

	@Autowired
	private HrDao hrDao;

	@Autowired
	private ProcessEngineFactoryBean processEngineFactory;
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	
	/**
	 * 新建任务
	 * 
	 */
	public Page<Object[]> processList( Page<Object[]> page,String category){
		
		   ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
		    		.latestVersion().active().orderByProcessDefinitionKey().asc();
		    
		    if (StringUtils.isNotEmpty(category)){
		    	processDefinitionQuery.processDefinitionCategory(category);
			}
		    
		    page.setCount(processDefinitionQuery.count());
		    
		    List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(), page.getMaxResults());
		    for (ProcessDefinition processDefinition : processDefinitionList) {
		      String deploymentId = processDefinition.getDeploymentId();
		      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
		      page.getList().add(new Object[]{processDefinition, deployment});
		    }
			return page;
	}
	/**
	 * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey){
		String formKey = "";
		if (StringUtils.isNotBlank(procDefId)){
			if (StringUtils.isNotBlank(taskDefKey)){
				try{
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				}catch (Exception e) {
					formKey = "";
				}
			}
			if (StringUtils.isBlank(formKey)){
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StringUtils.isBlank(formKey)){
				formKey = "/404";
			}
		}
		logger.debug("getFormKey: {}", formKey);
		return formKey;
	}
	/**
	 * 获取流程实例对象
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProcessInstance getProcIns(String procInsId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}
	
	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	public List<Hr> todoList(Hr hr){
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		
		List<Hr> result = new ArrayList<Hr>();
		
		// =============== 已经签收的任务  ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();
		
		// 设置查询条件
		if (StringUtils.isNotBlank(hr.getProcDefKey())){
			todoTaskQuery.processDefinitionKey(hr.getProcDefKey());
		}
		if (hr.getBeginDate() != null){
			todoTaskQuery.taskCreatedAfter(hr.getBeginDate());
		}
		if (hr.getEndDate() != null){
			todoTaskQuery.taskCreatedBefore(hr.getEndDate());
		}
		
		// 查询列表
		List<Task> todoList = todoTaskQuery.list();
		for (Task task : todoList) {
			Hr e = new Hr();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
//			e.setTaskVars(task.getTaskLocalVariables());
//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("todo");
			result.add(e);
		}
		
		// =============== 等待签收的任务  ===============
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
				.includeProcessVariables().active().orderByTaskCreateTime().desc();
		
		// 设置查询条件
		if (StringUtils.isNotBlank(hr.getProcDefKey())){
			toClaimQuery.processDefinitionKey(hr.getProcDefKey());
		}
		if (hr.getBeginDate() != null){
			toClaimQuery.taskCreatedAfter(hr.getBeginDate());
		}
		if (hr.getEndDate() != null){
			toClaimQuery.taskCreatedBefore(hr.getEndDate());
		}
		
		// 查询列表
		List<Task> toClaimList = toClaimQuery.list();
		for (Task task : toClaimList) {
			Hr e = new Hr();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
//			e.setTaskVars(task.getTaskLocalVariables());
//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("claim");
			result.add(e);
		}
		return result;
	}
	
	
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	public Page<Hr> historicList(Page<Hr> page, Hr hr){
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());

		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished()
				.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		
		// 设置查询条件
		if (StringUtils.isNotBlank(hr.getProcDefKey())){
			histTaskQuery.processDefinitionKey(hr.getProcDefKey());
		}
		if (hr.getBeginDate() != null){
			histTaskQuery.taskCompletedAfter(hr.getBeginDate());
		}
		if (hr.getEndDate() != null){
			histTaskQuery.taskCompletedBefore(hr.getEndDate());
		}
		
		// 查询总数
		page.setCount(histTaskQuery.count());
		
		// 查询列表
		List<HistoricTaskInstance> histList = histTaskQuery.listPage(page.getFirstResult(), page.getMaxResults());
		//处理分页问题
		List<Hr> hrList=Lists.newArrayList();
		for (HistoricTaskInstance histTask : histList) {
			Hr e = new Hr();
			e.setHistTask(histTask);
			e.setVars(histTask.getProcessVariables());
//			e.setTaskVars(histTask.getTaskLocalVariables());
//			System.out.println(histTask.getId()+"  =  "+histTask.getProcessVariables() + "  ========== " + histTask.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("finish");
			hrList.add(e);
			//page.getList().add(e);
		}
		page.setList(hrList);
		return page;
	}
}
