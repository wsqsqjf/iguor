package com.cn.ant.modules.product.service.fruit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ant.common.service.BaseService;
import com.cn.ant.modules.product.dao.fruit.FruitCategoryMapper;
import com.cn.ant.modules.product.entity.fruit.FruitCategory;

/**
 * 水果种类Service
 * 
 * @author AntDream
 * @version 2014-10-12
 */
@Component
@Transactional(readOnly = true)
public class FruitCategoryService extends BaseService {

	@Autowired
	private FruitCategoryMapper categoryMapper;
	
	public FruitCategory get(String id) {
		return categoryMapper.get(id);
	}
	
	public List<FruitCategory> find(Map<String, Object> params) {
		return categoryMapper.query(params);
	}
	
	@Transactional(readOnly = false)
	public void save(FruitCategory fruitCategory) {
		categoryMapper.insert(fruitCategory);
	}
	@Transactional(readOnly = false)
	public void update(FruitCategory fruitCategory) {
		categoryMapper.insert(fruitCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		categoryMapper.delete(id);
	}
	
}
