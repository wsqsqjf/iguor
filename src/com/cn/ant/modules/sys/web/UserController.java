package com.cn.ant.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.ant.common.beanvalidator.BeanValidators;
import com.cn.ant.common.config.Global;
import com.cn.ant.common.persistence.Page;
import com.cn.ant.common.utils.DateUtils;
import com.cn.ant.common.utils.StringUtils;
import com.cn.ant.common.utils.excel.ExportExcel;
import com.cn.ant.common.utils.excel.ImportExcel;
import com.cn.ant.common.web.BaseController;
import com.cn.ant.modules.sys.entity.Office;
import com.cn.ant.modules.sys.entity.Role;
import com.cn.ant.modules.sys.entity.User;
import com.cn.ant.modules.sys.service.SystemService;
import com.cn.ant.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

/**
 * 用户Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (user.getCompany() != null) {
			params.put("companyId", user.getCompany().getId());
		}
		if (user.getOffice() != null) {
			params.put("officeId", user.getOffice().getId());
		}
		params.put("loginName", user.getLoginName());
		params.put("name", user.getName());
		Page<User> page = systemService.findUser(new Page<User>(request, response), params);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, String oldLoginName, String newPassword, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(SystemService.entryptPassword(newPassword));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(oldLoginName, user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
		}
		if (UserUtils.getUser().getId().equals(id)){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(id)){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(id);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
	}
	
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Map<String, Object> params = new HashMap<String, Object>();
			Page<User> page = systemService.findUser(new Page<User>(request, response, -1), params);
			new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
    }

	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
    }
	
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
			new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
    }

	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	@RequiresUser
	@RequestMapping(value = "info")
	public String info(User user, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser = UserUtils.getUser(true);
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			systemService.saveUser(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		return "modules/sys/userInfo";
	}

	@RequiresUser
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
    
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
}
