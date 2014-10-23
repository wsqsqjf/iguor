package com.cn.ant.modules.product.web.fruit;

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

import com.cn.ant.common.persistence.Page;
import com.cn.ant.common.utils.Identities;
import com.cn.ant.common.utils.StringUtils;
import com.cn.ant.common.web.BaseController;
import com.cn.ant.modules.product.entity.fruit.Fruit;
import com.cn.ant.modules.product.service.fruit.FruitService;
import com.cn.ant.modules.sys.entity.User;
import com.cn.ant.modules.sys.utils.UserUtils;

/**
 * 水果Controller
 * 
 * @author AntDream
 * @version 2014-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/product/fruit/fruit")
public class FruitController extends BaseController {

	@Autowired
	private FruitService fruitService;
	
	@ModelAttribute
	public Fruit get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return fruitService.get(id);
		}else{
			return new Fruit();
		}
	}
	
	@RequiresPermissions("product:fruit:fruit:view")
	@RequestMapping(value = {"list", ""})
	public String list(Fruit fruit, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		fruit.setCreateBy(user.getId());
        Page<Fruit> page = fruitService.find(new Page<Fruit>(request, response), fruit); 
        model.addAttribute("page", page);
		return "modules/product/fruit/fruitList";
	}

	@RequiresPermissions("product:fruit:fruit:view")
	@RequestMapping(value = "form")
	public String form(Fruit fruit, Model model) {
		model.addAttribute("fruit", fruit);
		return "modules/product/fruit/fruitForm";
	}

	@RequiresPermissions("product:fruit:fruit:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(Fruit fruit, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, fruit)) {
				result = ERROR;
				msg = "数据校验失败，请重试！";
			} else {
				if (StringUtils.isBlank(fruit.getId())) {
					fruit.setId(Identities.generateUUID());
					fruit.prePersist();
					fruitService.save(fruit);
				} else {
					fruit.preUpdate();
					fruitService.update(fruit);
				}
				result = SUCCESS;
				msg = "保存水果'" + fruit.getName() + "'成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ERROR;
			msg = "操作失败，请联系管理员";
		}
		retMap.put("result", result);
		retMap.put("msg", msg);
		return retMap;
	}
	
	@RequiresPermissions("product:fruit:fruit:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String id, RedirectAttributes redirectAttributes) {
		try {
			fruitService.delete(id);
			msg = "删除水果成功！";
		} catch (Exception e) {
			e.printStackTrace();
			result = ERROR;
			msg = "删除水果失败！";
		}
		return result;
	}

}
