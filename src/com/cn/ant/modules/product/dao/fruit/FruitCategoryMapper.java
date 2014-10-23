package com.cn.ant.modules.product.dao.fruit;

import java.util.List;
import java.util.Map;

import com.cn.ant.common.persistence.MyBatisRepository;
import com.cn.ant.modules.product.entity.fruit.FruitCategory;

@MyBatisRepository
public interface FruitCategoryMapper {
	public int delete(String id);

    public int insert(FruitCategory record);

	public List<FruitCategory> query(Map<String, Object> params);

    public FruitCategory get(String id);

    public int update(FruitCategory record);
}