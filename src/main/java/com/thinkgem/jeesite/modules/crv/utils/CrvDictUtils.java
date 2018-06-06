package com.thinkgem.jeesite.modules.crv.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.crv.dao.CrvDictDao;
import com.thinkgem.jeesite.modules.crv.entity.CrvDict;


public class CrvDictUtils {

	private static CrvDictDao crvDictDao=SpringContextHolder.getBean(CrvDictDao.class);
	
	public static final String  CACHE_CRVDICT_MAP="crvDictMap";
	
	public static String getCrvDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (CrvDict crvDict : getCrvDictList(type)){
				if (type.equals(crvDict.getType()) && value.equals(crvDict.getLabel())){
					return crvDict.getValue();
				}
			}
		}
		return defaultValue;
	}

	public static List<CrvDict> getCrvDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<CrvDict>> crvDictMap = (Map<String, List<CrvDict>>)CacheUtils.get(CACHE_CRVDICT_MAP);
		if (crvDictMap==null){
			crvDictMap = Maps.newHashMap();
			for (CrvDict crvDict : crvDictDao.findAllList(new CrvDict())){
				List<CrvDict> crvDictList = crvDictMap.get(crvDict.getType());
				if (crvDictList != null){
					crvDictList.add(crvDict);
				}else{
					crvDictMap.put(crvDict.getType(), Lists.newArrayList(crvDict));
				}
			}
			CacheUtils.put(CACHE_CRVDICT_MAP, crvDictMap);
		}
		List<CrvDict> crvDictList = crvDictMap.get(type);
		if (crvDictList == null){
			crvDictList = Lists.newArrayList();
		}
		return crvDictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getCrvDictListJson(String type){
		return JsonMapper.toJsonString(getCrvDictList(type));
	}
}