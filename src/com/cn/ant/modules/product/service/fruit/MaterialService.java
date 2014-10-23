package com.cn.ant.modules.product.service.fruit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ant.common.persistence.Page;
import com.cn.ant.common.service.BaseService;
import com.cn.ant.modules.product.dao.fruit.MaterialMapper;
import com.cn.ant.modules.product.entity.fruit.Material;

/**
 * 素材Service
 * 
 * @author AntDream
 * @version 2014-10-16
 */
@Component
@Transactional(readOnly = true)
public class MaterialService extends BaseService {

	@Autowired
	private MaterialMapper materialMapper;
	
	public Material get(String id) {
		return materialMapper.get(id);
	}
	
	public Page<Material> find(Page<Material> page, Material material) {
		return null;
	}
	
	@Transactional(readOnly = false)
	public void save(Material material) {
		materialMapper.insert(material);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		materialMapper.delete(id);
	}
	
}
