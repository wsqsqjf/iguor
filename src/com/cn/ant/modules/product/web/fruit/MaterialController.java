package com.cn.ant.modules.product.web.fruit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.ant.common.config.Global;
import com.cn.ant.common.persistence.Page;
import com.cn.ant.common.utils.StringUtils;
import com.cn.ant.common.web.BaseController;
import com.cn.ant.modules.product.entity.fruit.Material;
import com.cn.ant.modules.product.service.fruit.MaterialFolderService;
import com.cn.ant.modules.product.service.fruit.MaterialService;
import com.cn.ant.modules.sys.entity.User;
import com.cn.ant.modules.sys.utils.UserUtils;

/**
 * 素材Controller
 * 
 * @author AntDream
 * @version 2014-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/product/fruit/material")
public class MaterialController extends BaseController {

	@Autowired
	private MaterialService materialService;
	@Autowired
	private MaterialFolderService folderService;
	
	@ModelAttribute
	public Material get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return materialService.get(id);
		}else{
			return new Material();
		}
	}
	
	@RequiresPermissions("product:fruit:material:edit")
	@RequestMapping(value = {"list", ""})
	public String list(Material material, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Material> page = materialService.find(new Page<Material>(request, response), material); 
        model.addAttribute("page", page);
		return "modules/product/fruit/materialList";
	}

	@RequiresPermissions("product:fruit:material:edit")
	@RequestMapping(value = "form")
	public String form(Material material, Model model) {
		User user = UserUtils.getUser();
		String folderJsonData = folderService.getJsonData(user.getOffice().getId());
		model.addAttribute("material", material);
		model.addAttribute("folderJsonData", folderJsonData);
		return "modules/product/fruit/materialForm";
	}

	@RequiresPermissions("product:fruit:material:edit")
	@RequestMapping(value = "save")
	public String save(Material material, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, material)){
			return form(material, model);
		}
		materialService.save(material);
		addMessage(redirectAttributes, "保存素材'" + material.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/modules/product/fruit/material/?repage";
	}
	
	@RequiresPermissions("product:fruit:material:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		materialService.delete(id);
		addMessage(redirectAttributes, "删除素材成功");
		return "redirect:"+Global.getAdminPath()+"/modules/product/fruit/material/?repage";
	}

}
