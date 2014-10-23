package com.cn.ant.modules.product.service.fruit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ant.common.persistence.Page;
import com.cn.ant.common.service.BaseService;
import com.cn.ant.modules.product.dao.fruit.FruitMapper;
import com.cn.ant.modules.product.entity.fruit.Fruit;

/**
 * 水果Service
 * 
 * @author AntDream
 * @version 2014-10-12
 */
@Component
@Transactional(readOnly = true)
public class FruitService extends BaseService {

	@Autowired
	private FruitMapper fruitMapper;
	
	public Fruit get(String id) {
		return fruitMapper.get(id);
	}
	
	public Page<Fruit> find(Page<Fruit> page, Fruit fruit) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (!page.isDisabled()) {
			page.setCount(fruitMapper.queryCount(params));
		}
		page.setList(fruitMapper.query(params));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Fruit fruit) {
		fruitMapper.insert(fruit);
	}
	
	@Transactional(readOnly = false)
	public void update(Fruit fruit) {
		fruitMapper.update(fruit);
	}

	@Transactional(readOnly = false)
	public void delete(String id) {
		fruitMapper.delete(id);
	}
	
}
