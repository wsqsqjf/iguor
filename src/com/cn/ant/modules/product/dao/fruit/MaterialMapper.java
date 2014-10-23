package com.cn.ant.modules.product.dao.fruit;

import com.cn.ant.common.persistence.MyBatisRepository;
import com.cn.ant.modules.product.entity.fruit.Material;

@MyBatisRepository
public interface MaterialMapper {
	public int delete(String id);

    public Material get(String id);

    public int update(Material record);

    public int insert(Material record);
}