package com.cn.ant.modules.product.entity.fruit;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cn.ant.common.persistence.MyDataEntity;

public class FruitCategory extends MyDataEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private FruitCategory parent;
	private String isHot;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public FruitCategory getParent() {
		return parent;
	}

	public void setParent(FruitCategory parent) {
		this.parent = parent;
	}

	public String getIsHot() {
		return isHot;
	}

    public void setIsHot(String isHot) {
		this.isHot = isHot == null ? null : isHot.trim();
	}

    public String getCreateBy() {
		return createBy;
	}

    public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

    public Date getCreateDate() {
		return createDate;
	}

    public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    public String getUpdateBy() {
		return updateBy;
	}

    public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

    public Date getUpdateDate() {
		return updateDate;
	}

    public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

    public String getRemarks() {
		return remarks;
	}

    public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public static void sortList(List<FruitCategory> list, List<FruitCategory> sourcelist, String parentId) {
		for (int i = 0; i < sourcelist.size(); i++) {
			FruitCategory e = sourcelist.get(i);
			if (e.getParent() != null && StringUtils.isNotBlank(e.getParent().getId()) && e.getParent().getId().equals(parentId)
					&& !e.getId().equals(parentId)) {
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j = 0; j < sourcelist.size(); j++) {
					FruitCategory child = sourcelist.get(j);
					if (child.getParent() != null && StringUtils.isNotBlank(e.getParent().getId()) && child.getParent().getId().equals(e.getId())) {
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}
}