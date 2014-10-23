package com.cn.ant.modules.product.entity.fruit;

import java.util.Date;

import com.cn.ant.common.persistence.MyDataEntity;

public class Material extends MyDataEntity {
	private static final long serialVersionUID = 1L;

	private String id;

	private String code;// 编号
	private String name;// 名称
	private Double size;// 大小
	private String type;// 类型
	private String stufix;// 后缀
	private String smallThumb;// 小图片地址
	private String mediumThumb;// 中等图地址
	private String largeThumb;// 大图地址
	private Double smallSize;// 小图大小
	private Double mediumSize;// 中图大小
	private Double largeSize;// 大图大小
	private String folder;// 种类
	private String officeId;// 机构编号
	private Date uploadTime;// 上传时间

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

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStufix() {
        return stufix;
    }

    public void setStufix(String stufix) {
        this.stufix = stufix == null ? null : stufix.trim();
    }

    public String getSmallThumb() {
        return smallThumb;
    }

    public void setSmallThumb(String smallThumb) {
        this.smallThumb = smallThumb == null ? null : smallThumb.trim();
    }

    public String getMediumThumb() {
        return mediumThumb;
    }

    public void setMediumThumb(String mediumThumb) {
        this.mediumThumb = mediumThumb == null ? null : mediumThumb.trim();
    }

    public String getLargeThumb() {
        return largeThumb;
    }

    public void setLargeThumb(String largeThumb) {
        this.largeThumb = largeThumb == null ? null : largeThumb.trim();
    }

    public Double getSmallSize() {
        return smallSize;
    }

    public void setSmallSize(Double smallSize) {
        this.smallSize = smallSize;
    }

    public Double getMediumSize() {
        return mediumSize;
    }

    public void setMediumSize(Double mediumSize) {
        this.mediumSize = mediumSize;
    }

    public Double getLargeSize() {
        return largeSize;
    }

    public void setLargeSize(Double largeSize) {
        this.largeSize = largeSize;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder == null ? null : folder.trim();
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId == null ? null : officeId.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}