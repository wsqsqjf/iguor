package com.cn.ant.common.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ant.common.persistence.Page;
import com.cn.ant.modules.sys.dao.AreaMapper;
import com.cn.ant.modules.sys.dao.MenuMapper;
import com.cn.ant.modules.sys.dao.OfficeMapper;
import com.cn.ant.modules.sys.dao.UserMapper;
import com.cn.ant.modules.sys.entity.Area;
import com.cn.ant.modules.sys.entity.Dict;
import com.cn.ant.modules.sys.entity.Menu;
import com.cn.ant.modules.sys.entity.Office;
import com.cn.ant.modules.sys.service.DictService;
import com.cn.ant.modules.sys.service.SystemService;

public class TestService extends SpringTransactionalContextTests {
    static Logger logger = Logger.getLogger(TestService.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OfficeMapper officeMapper;
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AreaMapper areaMapper;

    public void testSelect() {
		List<Office> list = new ArrayList<Office>();
		Office o1 = new Office();
		o1.setId("12");
		o1.setParentIds("0,1,7,1");
		Office o2 = new Office();
		o2.setId("11");
		o2.setParentIds("0,1,7,2");
		list.add(o1);
		list.add(o2);
		officeMapper.batchUpdate(list);
		System.out.println(1);
    }

	public void test2() {
		List<Menu> ms = menuMapper.findAllList();
		System.out.println(ms.size());
	}

	@Test
	public void testArea() {
		Area area = areaMapper.get("11");
		System.out.println(area.getName());
	}

    public static void main(String[] args) {
        DictService dictS = new DictService();
        Page<Dict> page = dictS.find(new Page<Dict>(1, 100), new Dict());
        List<Dict> lst = page.getList();
        System.out.println(lst.size());
    }


}
