package com.cn.ant.modules.product.web.fruit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.ant.common.utils.Identities;
import com.cn.ant.common.web.BaseController;
import com.cn.ant.modules.product.entity.fruit.FruitCategory;
import com.cn.ant.modules.product.service.fruit.FruitCategoryService;
import com.cn.ant.modules.sys.entity.User;
import com.cn.ant.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 水果种类Controller
 * 
 * @author AntDream
 * @version 2014-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/product/fruit/fruitCategory")
public class FruitCategoryController extends BaseController {

	@Autowired
	private FruitCategoryService fruitCategoryService;
	
	@ModelAttribute
	public FruitCategory get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return fruitCategoryService.get(id);
		}else{
			return new FruitCategory();
		}
	}
	
	@RequiresPermissions("product:fruit:fruitCategory:edit")
	@RequestMapping(value = {"list", ""})
	public String list(FruitCategory fruitCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		FruitCategory category = new FruitCategory();
		category.setId("1");
		List<FruitCategory> list = Lists.newArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		List<FruitCategory> sourcelist = fruitCategoryService.find(params);
		FruitCategory.sortList(list, sourcelist, category.getId());
		model.addAttribute("list", list);
		model.addAttribute("rootcategory", category);
		model.addAttribute("fruitCategory", fruitCategory);
		return "modules/product/fruit/fruitCategoryList";
	}

	@RequiresPermissions("product:fruit:fruitCategory:edit")
	@RequestMapping(value = "form")
	public String form(FruitCategory fruitCategory, Model model) {
		User user = UserUtils.getUser();
		FruitCategory category = new FruitCategory();
		category.setId("1");
		List<FruitCategory> list = Lists.newArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		List<FruitCategory> sourcelist = fruitCategoryService.find(params);
		FruitCategory.sortList(list, sourcelist, category.getId());
		model.addAttribute("list", list);
		model.addAttribute("rootcategory", category);
		model.addAttribute("fruitCategory", fruitCategory);
		return "modules/product/fruit/fruitCategoryForm";
	}

	@RequiresPermissions("product:fruit:fruitCategory:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(FruitCategory fruitCategory, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, fruitCategory)) {
				result = ERROR;
				msg = "数据校验失败,请重试!";
			} else {
				if (StringUtils.isBlank(fruitCategory.getId())) {
					fruitCategory.setId(Identities.generateUUID());
					fruitCategory.prePersist();
					fruitCategoryService.save(fruitCategory);
				} else {
					fruitCategory.preUpdate();
					fruitCategoryService.update(fruitCategory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ERROR;
			msg = "操作失败,请联系管理员";
		}
		retMap.put("result", result);
		retMap.put("msg", msg);
		return retMap;
	}
	
	@RequiresPermissions("product:fruit:fruitCategory:edit")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String id, RedirectAttributes redirectAttributes) {
		try {
			fruitCategoryService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			result = ERROR;
		}
		return result;
	}

	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = UserUtils.getUser();
		List<FruitCategory> list = fruitCategoryService.find(null);
		for (int i = 0; i < list.size(); i++) {
			FruitCategory e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParent() != null ? e.getParent().getId() : 0);
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}
}
