package com.cn.ant.modules.product.entity.fruit;

import java.util.Date;

import com.cn.ant.common.persistence.MyDataEntity;

/**
 * 水果Entity
 * 
 * @author AntDream
 * @version 2014-10-12
 */
public class Fruit extends MyDataEntity {
	
	private static final long serialVersionUID = 1L;
	private String id;

	private String name;
	/**
	 * 简介
	 */
	private String summary;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 产地
	 */
	private String origin;
	/**
	 * 种类
	 */
	private FruitCategory category;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 单价
	 */
	private Double price;
	/**
	 * 单价内数量
	 */
	private Double number;
	/**
	 * 促销价
	 */
	private Double promotionPrice;
	/**
	 * 促销价内数量
	 */
	private Double promotionNum;
	/**
	 * 折扣
	 */
	private Double discount;
	/**
	 * 是否显示促销价
	 */
	private String showPromotion;
	/**
	 * 进含量
	 */
	private Double weight;
	/**
	 * 生产日期
	 */
	private Date productDate;
	/**
	 * 保质期
	 */
	private Integer shelfLife;
	/**
	 * 包装方式
	 */
	private String packingWay;
	/**
	 * 是否有机食品
	 */
	private String isOrganic;

	/**
	 * 是否热门
	 */
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary == null ? null : summary.trim();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand == null ? null : brand.trim();
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin == null ? null : origin.trim();
	}

	public FruitCategory getCategory() {
		return category;
	}

	public void setCategory(FruitCategory category) {
		this.category = category;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Double getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Double getPromotionNum() {
		return promotionNum;
	}

	public void setPromotionNum(Double promotionNum) {
		this.promotionNum = promotionNum;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getShowPromotion() {
		return showPromotion;
	}

	public void setShowPromotion(String showPromotion) {
		this.showPromotion = showPromotion == null ? null : showPromotion.trim();
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public Integer getShelfLife() {
		return shelfLife;
	}

    public void setShelfLife(Integer shelfLife) {
		this.shelfLife = shelfLife;
	}

    public String getPackingWay() {
		return packingWay;
	}

    public void setPackingWay(String packingWay) {
		this.packingWay = packingWay == null ? null : packingWay.trim();
	}

    public String getIsOrganic() {
		return isOrganic;
	}

    public void setIsOrganic(String isOrganic) {
		this.isOrganic = isOrganic == null ? null : isOrganic.trim();
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
}


