package com.cn.ant.modules.product.dao.fruit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.ant.common.persistence.MyBatisRepository;
import com.cn.ant.modules.product.entity.fruit.MaterialFolder;

@MyBatisRepository
public interface MaterialFolderMapper {
	public int delete(String id);

	public int insert(MaterialFolder record);

	public MaterialFolder get(String id);

	public int update(MaterialFolder record);

	public List<MaterialFolder> query(@Param("officeId") String officeId);

}