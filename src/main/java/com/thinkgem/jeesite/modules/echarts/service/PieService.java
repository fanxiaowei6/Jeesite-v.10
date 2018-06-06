package com.thinkgem.jeesite.modules.echarts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.echarts.Entity.Pie;
import com.thinkgem.jeesite.modules.echarts.dao.PieDao;

@Service
@Transactional(readOnly = true)
public class PieService extends CrudService<PieDao, Pie> {

	public Pie get(String id){
		return super.get(id);
	}
	
	public List<Pie> findList(Pie pie){
		
		return super.findList(pie);
	}
	
	public Page<Pie> findPage(Page<Pie> page,Pie pie){
		return super.findPage(page, pie);
	}
	@Transactional(readOnly = false)
	public void delete(Pie pie) {
		super.delete(pie);
	}
	@Transactional(readOnly = false)
	public void save(Pie pie) {
		super.save(pie);
	}
}
