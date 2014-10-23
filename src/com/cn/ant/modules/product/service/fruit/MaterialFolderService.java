package com.cn.ant.modules.product.service.fruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ant.common.mapper.JsonMapper;
import com.cn.ant.common.service.BaseService;
import com.cn.ant.modules.product.dao.fruit.MaterialFolderMapper;
import com.cn.ant.modules.product.entity.fruit.MaterialFolder;

/**
 * 素材种类Service
 * 
 * @author AntDream
 * @version 2014-10-16
 */
@Component
@Transactional(readOnly = true)
public class MaterialFolderService extends BaseService {

	@Autowired
	private MaterialFolderMapper materialFolderMapper;
	
	public MaterialFolder get(String id) {
		return materialFolderMapper.get(id);
	}
	
	public List<MaterialFolder> find(String officeId) {
		return materialFolderMapper.query(officeId);
	}
	
	@Transactional(readOnly = false)
	public void save(MaterialFolder materialFolder) {
		materialFolderMapper.insert(materialFolder);
	}
	
	@Transactional(readOnly = false)
	public void update(MaterialFolder materialFolder) {
		materialFolderMapper.update(materialFolder);
	}

	@Transactional(readOnly = false)
	public void delete(String id) {
		materialFolderMapper.delete(id);
	}
	
	public String getJsonData(String officeId) {
		List<MaterialFolder> page = materialFolderMapper.query(officeId);
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap = null;
		MaterialFolder tempFolder = null;
		for (int i = 0; i < page.size(); i++) {
			tempMap = new HashMap<String, Object>();
			tempFolder = page.get(i);
			tempMap.put("id", tempFolder.getId());
			tempMap.put("pId", tempFolder.getParent().getId());
			tempMap.put("name", tempFolder.getName());
			tempMap.put("open", true);
			tempList.add(tempMap);
		}
		String jsonStr = JsonMapper.getInstance().toJson(tempList);
		return jsonStr;
	}

}
