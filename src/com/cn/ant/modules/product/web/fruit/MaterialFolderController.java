package com.cn.ant.modules.product.web.fruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.ant.common.mapper.JsonMapper;
import com.cn.ant.common.utils.Identities;
import com.cn.ant.common.utils.StringUtils;
import com.cn.ant.common.web.BaseController;
import com.cn.ant.modules.product.entity.fruit.MaterialFolder;
import com.cn.ant.modules.product.service.fruit.MaterialFolderService;
import com.cn.ant.modules.sys.entity.User;
import com.cn.ant.modules.sys.utils.UserUtils;

/**
 * 素材种类Controller
 * 
 * @author AntDream
 * @version 2014-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/product/fruit/materialFolder")
public class MaterialFolderController extends BaseController {

	@Autowired
	private MaterialFolderService materialFolderService;
	
	@ModelAttribute
	public MaterialFolder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return materialFolderService.get(id);
		}else{
			return new MaterialFolder();
		}
	}
	
	@RequiresPermissions("product:fruit:materialFolder:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public String list(MaterialFolder materialFolder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		List<MaterialFolder> page = materialFolderService.find(user.getOffice().getId());
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap = null;
		MaterialFolder tempFolder = null;
		for (int i = 0; i < page.size(); i++) {
			tempMap = new HashMap<String, Object>();
			tempFolder = page.get(i);
			tempMap.put("id", tempFolder.getId());
			tempMap.put("pId", tempFolder.getParent().getId());
			tempMap.put("name", tempFolder.getName());
			tempMap.put("open", true);
			tempList.add(tempMap);
		}
		String jsonStr = JsonMapper.getInstance().toJson(tempList);
		return jsonStr;
	}

	@RequiresPermissions("product:fruit:materialFolder:view")
	@RequestMapping(value = "form")
	public String form(MaterialFolder materialFolder, Model model) {
		model.addAttribute("materialFolder", materialFolder);
		return "modules/product/fruit/materialFolderForm";
	}

	@RequiresPermissions("product:fruit:materialFolder:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(MaterialFolder materialFolder, Model model, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			if (StringUtils.isNotBlank(materialFolder.getId())) {
				materialFolderService.update(materialFolder);
			} else {
				materialFolder.setId(Identities.generateUUID());
				materialFolder.setOfficeId(user.getOffice().getId());
				materialFolderService.save(materialFolder);
				retMap.put("nodeId", materialFolder.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ERROR;
			msg = "操作异常,请联系管理员!";
		}
		retMap.put("result", result);
		retMap.put("msg", msg);
		return retMap;
	}
	
	@RequiresPermissions("product:fruit:materialFolder:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String id, RedirectAttributes redirectAttributes) {
		try {
			materialFolderService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

}
