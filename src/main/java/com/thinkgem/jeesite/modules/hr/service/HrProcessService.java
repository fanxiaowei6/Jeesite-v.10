package com.thinkgem.jeesite.modules.hr.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
@Service
@Transactional(readOnly=true)
public class HrProcessService extends BaseService {
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	/**
	 * 流程定义列表
	 * 
	 */
	@Transactional(readOnly=false)
	public Page<Object[]> processList(Page<Object[]> page,String category){
		 ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
		    		.latestVersion().orderByProcessDefinitionKey().asc();
		    
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
	 * 挂起、激活流程实例
	 * 
	 */
	@Transactional(readOnly=false)
	public String updateState(String state,String procDefId){
		if(state.equals("active")){
			repositoryService.activateProcessDefinitionById(procDefId, false, null);
			return "已激活ID为[" + procDefId + "]的流程定义。";
		}
		if(state.equals("suspend")){
			repositoryService.suspendProcessDefinitionById(procDefId, false, null);
			return "已激活ID为["+procDefId+"]的流程定义。";
		}
		return "hello";
	}
	
	/**
	 * 删除部署的流程
	 * 
	 */
	@Transactional(readOnly=false)
	public void deleteDeployment(String deploymentId){
		repositoryService.deleteDeployment(deploymentId, true);
	}
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	@Transactional(readOnly = false)
	public org.activiti.engine.repository.Model convertToModel(String procDefId) throws UnsupportedEncodingException, XMLStreamException {
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
		processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
	
		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modelNode = converter.convertToJson(bpmnModel);
		org.activiti.engine.repository.Model modelData = repositoryService.newModel();
		modelData.setKey(processDefinition.getKey());
		modelData.setName(processDefinition.getResourceName());
		modelData.setCategory(processDefinition.getCategory());//.getDeploymentId());
		modelData.setDeploymentId(processDefinition.getDeploymentId());
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));
	
		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());
	
		repositoryService.saveModel(modelData);
	
		repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
	
		return modelData;
	}
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
	@Transactional(readOnly = false)
	public String deploy(String exportDir, String category, MultipartFile file) {

		String message = "";
		
		String fileName = file.getOriginalFilename();
		
		try {
			InputStream fileInputStream = file.getInputStream();
			Deployment deployment;
			String extension = FilenameUtils.getExtension(fileName);
			if (extension.equals("zip") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
			} else if (extension.equals("png")) {
				deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
			} else if (fileName.indexOf("bpmn20.xml") != -1) {
				deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
			} else if (extension.equals("bpmn")) { // bpmn扩展名特殊处理，转换为bpmn20.xml
				String baseName = FilenameUtils.getBaseName(fileName); 
				deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
			} else {
				message = "不支持的文件类型：" + extension;
				return message;
			}
			
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

			// 设置流程分类
			for (ProcessDefinition processDefinition : list) {
//					ActUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
				message += "部署成功，流程ID=" + processDefinition.getId() + "<br/>";
			}
			
			if (list.size() == 0){
				message = "部署失败，没有流程。";
			}
			
		} catch (Exception e) {
			throw new ActivitiException("部署失败！", e);
		}
		return message;
	}
	/**
	 * 流程定义列表
	 */
	public Page<ProcessInstance> runningList(Page<ProcessInstance> page, String procInsId, String procDefKey) {

	    ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();

	    if (StringUtils.isNotBlank(procInsId)){
		    processInstanceQuery.processInstanceId(procInsId);
	    }
	    
	    if (StringUtils.isNotBlank(procDefKey)){
		    processInstanceQuery.processDefinitionKey(procDefKey);
	    }
	    
	    page.setCount(processInstanceQuery.count());
	    page.setList(processInstanceQuery.listPage(page.getFirstResult(), page.getMaxResults()));
		return page;
	}
}
