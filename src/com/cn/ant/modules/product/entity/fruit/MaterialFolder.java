package com.cn.ant.modules.product.entity.fruit;

import com.cn.ant.common.persistence.MyDataEntity;

public class MaterialFolder extends MyDataEntity {
	private static final long serialVersionUID = 1L;

	private String id;

	private String code;// 编号

    private String name;// 名称

    private MaterialFolder parent;// 上级目录

    private String officeId;// 机构编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public MaterialFolder getParent() {
		return parent;
	}

	public void setParent(MaterialFolder parent) {
		this.parent = parent;
	}

	public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId == null ? null : officeId.trim();
    }
}