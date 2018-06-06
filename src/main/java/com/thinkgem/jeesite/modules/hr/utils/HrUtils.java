package com.thinkgem.jeesite.modules.hr.utils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hr.entity.Hr;

public class HrUtils {

	/**
	 * 获取流程表单URL
	 * @param formKey
	 * @param act 表单传递参数
	 * @return
	 */
	public static String getFormUrl(String formKey, Hr hr){
		
		StringBuilder formUrl = new StringBuilder();
		
		String formServerUrl = Global.getConfig("activiti.form.server.url");
		if (StringUtils.isBlank(formServerUrl)){
			formUrl.append(Global.getAdminPath());
		}else{
			formUrl.append(formServerUrl);
		}
		
		formUrl.append(formKey).append(formUrl.indexOf("?") == -1 ? "?" : "&");
		formUrl.append("act.taskId=").append(hr.getTaskId() != null ? hr.getTaskId() : "");
		formUrl.append("&act.taskName=").append(hr.getTaskName() != null ? Encodes.urlEncode(hr.getTaskName()) : "");
		formUrl.append("&act.taskDefKey=").append(hr.getTaskDefKey() != null ? hr.getTaskDefKey() : "");
		formUrl.append("&act.procInsId=").append(hr.getProcInsId() != null ? hr.getProcInsId() : "");
		formUrl.append("&act.procDefId=").append(hr.getProcDefId() != null ? hr.getProcDefId() : "");
		formUrl.append("&act.status=").append(hr.getStatus() != null ? hr.getStatus() : "");
		formUrl.append("&id=").append(hr.getBusinessId() != null ? hr.getBusinessId() : "");
		
		return formUrl.toString();
	}
	
}
