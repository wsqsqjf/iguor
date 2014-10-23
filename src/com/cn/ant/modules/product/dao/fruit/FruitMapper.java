package com.cn.ant.modules.product.dao.fruit;

import java.util.List;
import java.util.Map;

import com.cn.ant.common.persistence.MyBatisRepository;
import com.cn.ant.modules.product.entity.fruit.Fruit;

@MyBatisRepository
public interface FruitMapper {
	public int delete(String id);

    public int insert(Fruit record);

    public Fruit get(String id);

    public int update(Fruit record);

	public List<Fruit> query(Map<String, Object> params);
	public int queryCount(Map<String, Object> params);
}