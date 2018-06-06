package com.thinkgem.jeesite.modules.map.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="${adminPath}/map/BaiduMap")
public class BaiduMapController {
	
	@RequiresPermissions("map:BaiduMap:view")
	@RequestMapping(value={"list",""})
	public String list(HttpServletRequest request,HttpServletResponse response,Model model){
		
		return "modules/map/BaiduMap";
	}

}
