/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.cn.ant.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ant.common.persistence.Page;
import com.cn.ant.common.service.BaseService;
import com.cn.ant.common.utils.CacheUtils;
import com.cn.ant.modules.sys.dao.DictMapper;
import com.cn.ant.modules.sys.entity.Dict;
import com.cn.ant.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * 
 * @author ThinkGem
 * @version 2013-5-29
 */
@Service
@Transactional(readOnly = true)
public class DictService extends BaseService {

    @Autowired
	private DictMapper dictMapper;
	
	public Dict get(String id) {
		return dictMapper.get(id);
	}
	
	public Page<Dict> find(Page<Dict> page, Dict dict) {
		page.setList(dictMapper.findAll());
		return page;
	}
	
    public List<Dict> findAll() {
		List<Dict> list = dictMapper.findAll();
        return list;
    }

	public List<String> findTypeList() {
		return dictMapper.findTypeList();
	}
	
	@Transactional(readOnly = false)
	public void save(Dict dict) {
		dictMapper.insert(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		dictMapper.delete(id);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
}
