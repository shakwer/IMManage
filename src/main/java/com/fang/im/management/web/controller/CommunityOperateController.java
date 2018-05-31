package com.fang.im.management.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fang.common.utils.OutParameterUtils;
import com.fang.common.utils.StringUtils;
import com.fang.im.management.po.*;
import com.fang.im.management.service.*;
import com.fang.im.management.service.impl.CommunityServiceImpl;
import com.fang.im.management.shiro.FangOaAuthenAndAuthorRealm;
import com.fang.im.management.systemexception.CustomeBusinessJsonExciption;
import com.fang.im.management.utils.ImgUpLoadUtils;
import com.fang.im.management.utils.PhoneNOUtil;
import com.fang.im.management.web.vo.in.AddCommunityInVo;
import com.fang.im.management.web.vo.in.CommunityManagerInVo;
import com.fang.im.management.web.vo.in.UpdateCommunityInVo;
import com.fang.im.management.web.vo.out.CommunityManagerOutVo;
import com.fang.im.management.web.vo.out.ImUserServiceInterfaceUrlOut;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 群操作控制器
 *
 * @author YZT_SouFun
 */
@Controller
public class CommunityOperateController {

	/**
	 * 城市信息操作
	 */
	@Autowired
	private SysCityOperateService sysCityOperateService;

	/**
	 * 用户信息操作
	 */
	@Autowired
	private SysUserOperateService sysUserOperateService;

	/**
	 * 群操作
	 */
	@Autowired
	private CommunityOperateService communityOperateService;

	/**
	 * 分类操作
	 */
	@Autowired
	private SysCommunityCategoryService sysCommunityCategoryService;

	/**
	 * 获取im接口
	 */
	@Autowired
	private CommunityServiceImpl communityServiceImpl;

	/**
	 * 自定义realm
	 */
	@Autowired
	private FangOaAuthenAndAuthorRealm realm;

	/**
	 * 获取大区和城市信息
	 **/
	@Autowired
	private SysRegionOperateService regionOperateService;

	private enum JsonObjectRespone {
		result, message, data;
	}

	/**
	 * 获取展示列表
	 *
	 * @param model
	 *            model
	 * @param inVo
	 *            inVo
	 * @return 页面
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/community.html")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	public String communityManagePage(Model model, CommunityManagerInVo inVo) throws Exception {
		SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
		String roleName = currentLoginUser.getSysRole().getRoleName();
		System.err
				.println("a---------------" + roleName + "-------" + inVo.getIsdelete() + "----" + inVo.getPageindex());
		List<SysCommunityResource> communitylists = new ArrayList<SysCommunityResource>();
		List<CommunityManagerOutVo> outmodel = new ArrayList<CommunityManagerOutVo>();
		Integer pageSize = 20;
		Integer pageIndex = 1;
		Integer totalcount = 0;
		boolean isdelete = false;
		if (inVo.getPageindex() != null) {
			pageIndex = inVo.getPageindex();
		}
		if (inVo.getIsdelete() != null && inVo.getIsdelete() == 1) {
			isdelete = true;
		}

		if (roleName.equals("超级管理员")) {
			// 查询全部
			totalcount = communityOperateService.getCountByIsDelete("", null, isdelete);
			if (totalcount != null && totalcount > 0) {
				communitylists = communityOperateService.getCommunityByIsDelete("", null, isdelete, pageSize, pageIndex,
						inVo.getOrderstr());
			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}

		} else if (roleName.equals("管理员")) {
			// 查询部分城市
			Set<SysCity> cityidset = currentLoginUser.getSysCities();
			List<Integer> cityidlist = new ArrayList<Integer>();
			if (cityidset != null && cityidset.size() > 0) {
				for (SysCity item : cityidset) {
					cityidlist.add(item.getSysCityId());
				}
			}
			totalcount = communityOperateService.getCountByIsDelete("", cityidlist, isdelete);
			if (totalcount != null && totalcount > 0) {
				communitylists = communityOperateService.getCommunityByIsDelete("", cityidlist, isdelete, pageSize,
						pageIndex, inVo.getOrderstr());
			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}
		} else {
			// 查询部分城市
			Set<SysCity> cityidset = currentLoginUser.getSysCities();
			List<Integer> cityidlist = new ArrayList<Integer>();
			if (cityidset != null && cityidset.size() > 0) {
				for (SysCity item : cityidset) {
					cityidlist.add(item.getSysCityId());
				}
			}
			// 只查看自己创建的群
			totalcount = communityOperateService.getCountByIsDelete(currentLoginUser.getEmail(), cityidlist, isdelete);
			if (totalcount != null && totalcount > 0) {
				communitylists = communityOperateService.getCommunityByIsDelete(currentLoginUser.getEmail(), cityidlist,
						isdelete, pageSize, pageIndex, inVo.getOrderstr());
			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}
		}

		// // 得到群类型集合
		// List<SysCommunityCategory> catglist =
		// sysCommunityCategoryService.getAllCategoryList();
		// // 得到大区和城市的集合
		// List<SysRegion> sysRegionList =
		// regionOperateService.getAllSysRegionList();
		// System.out.println("yxdyxd------" + regionOperateService + "---" +
		// sysRegionList.size());
		// for (SysRegion region : sysRegionList) {
		// for(SysCity city : region.getCitys()){
		// System.out.println("a--------" + city.getCityName());
		// }
		// }

		// 正常的表
		model.addAttribute("totalcount", totalcount);
		model.addAttribute("orderstr", inVo.getOrderstr());
		model.addAttribute("totalpage", totalcount / pageSize + 1);
		model.addAttribute("pageindex", pageIndex);
		model.addAttribute("communitylists", outmodel);
		model.addAttribute("isdelete", isdelete == true ? 1 : 0);
		model.addAttribute("cities", currentLoginUser.getSysCities());
		// model.addAttribute("catglist", catglist);
		// model.addAttribute("sysRegionList", sysRegionList);
		// 删除的表

		// 调用接口

		return "views/communitymanage";
	}

	/**
	 * 搜索群类型
	 * 
	 * @throws Exception
	 **/
	@RequestMapping(value = "/getAllSysCommunityCategory")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody
	public JSONObject getAllSysCommunityCategory() throws Exception {
		JSONObject jsonobj = new JSONObject();
		jsonobj.put(JsonObjectRespone.message.name(), "成功");
		jsonobj.put(JsonObjectRespone.result.name(), "1");
		// 得到群类型集合
		List<SysCommunityCategory> catglist = sysCommunityCategoryService.getAllCategoryList();
		jsonobj.put(JsonObjectRespone.data.name(), catglist);
		return jsonobj;
	}

	/**
	 * 得到所有大区和城市的结合
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllSysRegion")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody
	public JSONObject getAllSysRegionList() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(JsonObjectRespone.message.name(), "成功");
		jsonObject.put(JsonObjectRespone.result.name(), "1");
		// 得到大区和城市的集合
		List<SysRegion> sysRegionList = regionOperateService.getAllSysRegionList();
		jsonObject.put(JsonObjectRespone.data.name(), sysRegionList);
		return jsonObject;
	}

	/**
	 * 获取展示列表
	 *
	 * @param model
	 *            model
	 * @param inVo
	 *            inVo
	 * @return 页面
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/searchcommunity")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	public String searchCommunityPage(Model model, CommunityManagerInVo inVo) throws Exception {
		//inVo.setCommunityname(new String(inVo.getCommunityname().getBytes("iso8859-1"), "utf-8"));
		SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
		String roleName = currentLoginUser.getSysRole().getRoleName();

		List<SysCommunityResource> communitylists = new ArrayList<SysCommunityResource>();
		List<CommunityManagerOutVo> outmodel = new ArrayList<CommunityManagerOutVo>();
		Integer pageSize = 20;
		Integer pageIndex = 1;
		Long totalcount = 0L;
		boolean isdelete = false;
		if (inVo.getPagesize() != null || inVo.getPagesize() != null) {
			if (inVo.getPagesize() > 150 || inVo.getPagesize() < 1) {
				throw new CustomeBusinessJsonExciption("页码输入有误");
			}
			pageSize = inVo.getPagesize();
			pageIndex = inVo.getPageindex();
		}
		if (inVo.getIsdelete() != null && inVo.getIsdelete() == 1) {
			isdelete = true;
		}
		

		if (roleName.equals("超级管理员")) {
			// 查询全部
			// totalcount =
			// communityOperateService.getCountLikeNameByParam(inVo.getCommunityname(),
			// null, null, isdelete);
			// inVo.setCommunityid("10039165");
			// System.err.println("a-------------------------------" +
			// inVo.getProjectid()+"---"+inVo.getCityId()+"----"+inVo.getCategory()+"---"+inVo.getRegionId()+"---"+inVo.getCommunityid()+"---"+inVo.getCommunityname());
			totalcount = communityOperateService.searchCommunityCount(inVo.getProjectid(), inVo.getCategory(),
					inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(), isdelete);
			if (totalcount != null && totalcount > 0) {
				// communitylists =
				// communityOperateService.getCommunityLikeNameByParam(inVo.getCommunityname(),
				// pageIndex,
				// pageSize, inVo.getOrderstr(), null, null, isdelete);
				communitylists = communityOperateService.searchCommunity(inVo.getProjectid(), inVo.getCategory(),
						inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(),
						pageIndex, pageSize, isdelete);
			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}

		} else if (roleName.equals("管理员")) {
			Set<SysCity> cities = currentLoginUser.getSysCities();
			Collection<Integer> cityIds = new ArrayList<Integer>();
			for (SysCity city : cities) {
				cityIds.add(city.getSysCityId());
			}

			// 查询全部
			// totalcount =
			// communityOperateService.getCountLikeNameByParam(inVo.getCommunityname(),
			// null, cityIds,
			// isdelete);
			totalcount = communityOperateService.searchCommunityCount(inVo.getProjectid(), inVo.getCategory(),
					inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(), isdelete);
			if (totalcount != null && totalcount > 0) {
				// communitylists =
				// communityOperateService.getCommunityLikeNameByParam(inVo.getCommunityname(),
				// pageIndex,
				// pageSize, inVo.getOrderstr(), null, cityIds, isdelete);
				communitylists = communityOperateService.searchCommunity(inVo.getProjectid(), inVo.getCategory(),
						inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(), pageIndex,
						pageSize, isdelete);
			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}
		} else {
			Set<SysCity> cities = currentLoginUser.getSysCities();
			Collection<Integer> cityIds = new ArrayList<Integer>();
			for (SysCity city : cities) {
				cityIds.add(city.getSysCityId());
			}
			// 查询全部
			// totalcount =
			// communityOperateService.getCountLikeNameByParam(inVo.getCommunityname(),
			// currentLoginUser.getEmail(), cityIds, isdelete);
			totalcount = communityOperateService.searchCommunityCount(inVo.getProjectid(), inVo.getCategory(),
					inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(), isdelete);
			if (totalcount != null && totalcount > 0) {
				// communitylists =
				// communityOperateService.getCommunityLikeNameByParam(inVo.getCommunityname(),
				// pageIndex,
				// pageSize, inVo.getOrderstr(), currentLoginUser.getEmail(),
				// cityIds, isdelete);

			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}
		}
		// 正常的表
		model.addAttribute("totalcount", totalcount);
		model.addAttribute("totalpage", totalcount / pageSize + 1);
		model.addAttribute("pageindex", pageIndex);
		model.addAttribute("communitylists", outmodel);
		model.addAttribute("isdelete", isdelete == true ? 1 : 0);
		model.addAttribute("cities", currentLoginUser.getSysCities());
		model.addAttribute("searchtext", inVo.getCommunityname());
		// 删除的表

		// 调用接口

		return "views/communitymanage";
	}

	/**
	 * 添加群方法
	 *
	 * @param inVo
	 *            inVo
	 * @return json
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/addcommunity")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody
	public JSONObject addCommunity(AddCommunityInVo inVo) throws Exception {
//		inVo.setCommunityname(new String(inVo.getCommunityname().getBytes("iso8859-1"), "utf-8"));
//		inVo.setNotice(new String(inVo.getNotice().getBytes("iso8859-1"), "utf-8"));
		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) subject.getPrincipal();
		String roleName = currentLoginUser.getSysRole().getRoleName();
		Set<SysCity> cities = currentLoginUser.getSysCities();
		SysCommunityResource community = new SysCommunityResource();
		boolean urlsuccess = false;
		boolean sqlsuccess = false;

		JSONObject jsonobj = new JSONObject();

		if (StringUtils.isEmpty(inVo.getCommunityname()) || inVo.getCategory() == null || inVo.getCity() == null) {
			throw new CustomeBusinessJsonExciption("请检查添加内容");
		}
		// if (StringUtils.isEmpty(inVo.getGrouplist())) {
		// throw new CustomeBusinessJsonExciption("请添加成员");
		// }

		if (inVo.getCommunityname().length() < 2 || inVo.getCommunityname().length() > 10) {
			throw new CustomeBusinessJsonExciption("群名称为2到10位");
		}
		if (!StringUtils.isEmpty(inVo.getNotice())) {
			if (inVo.getNotice().length() < 15 || inVo.getNotice().length() > 300) {
				throw new CustomeBusinessJsonExciption("群公告为15到300位字符");
			}
		}

		if (roleName.equals("普通群主")) {
			cities = currentLoginUser.getSysCities();
			boolean isHasPermission = false;
			for (SysCity city : cities) {
				if (city.getSysCityId().equals(inVo.getCity())) {
					isHasPermission = true;
					break;
				}
			}
			if (!isHasPermission) {
				throw new CustomeBusinessJsonExciption("没有该城市权限");
			}
		} else if (roleName.equals("管理员")) {
			if (!subject.isPermitted("user:addcity:" + inVo.getCity())) {
				throw new CustomeBusinessJsonExciption("缺少城市权限");
			}
		}

		String imuser = "";

		SysUserResource sr = sysUserOperateService.getUserByEmail(currentLoginUser.getEmail());
		if (sr != null) {
			imuser = sr.getImUserName();
		}

		// 过滤敏感词
		// 调用接口

		Integer imid = communityServiceImpl.addCommunityFromImUrl(imuser, inVo.getCommunityname(), inVo.getGrouplist());
		if (imid == 0) {
			throw new CustomeBusinessJsonExciption("添加失败");
		} else {
			urlsuccess = true;
		}

		String communityid = imid.toString();
		String communityHolder = currentLoginUser.getEmail();
		String communityName = inVo.getCommunityname();
		Integer city = inVo.getCity();
		Integer category = inVo.getCategory();
		Date createTime = new Date();
		sqlsuccess = communityOperateService.addCommunity(communityid, communityHolder, communityName, city, category,
				createTime, inVo.getProjectid());
		community.setCategory(category);
		community.setCity(city);
		community.setCommunityHolder(communityHolder);
		community.setCommunityId(communityid);
		community.setCommunityName(communityName);
		community.setCreateTime(createTime);
		community.setIsDelete(false);

		String qrcode = "";

		// 异常
		try {
			// 设置群图像
			if (!StringUtils.isEmpty(inVo.getPic())) {
				communityServiceImpl.updateCommunityFromImUrl(imuser, communityid, "setgrouppic", inVo.getPic());
			}
			// 设置公告
			if (!StringUtils.isEmpty(inVo.getNotice())) {
				communityServiceImpl.updateCommunityFromImUrl(imuser, communityid, "setgrouppic", inVo.getPic());
			}

			// if (!StringUtils.isEmpty(inVo.getProjectid())) {
			// communityServiceImpl.updateCommunityFromImUrl(imuser,
			// communityid, "setProjectid", inVo.getPic());
			// }

			// 设置限制
			communityServiceImpl.updateCommunityFromImUrl(imuser, communityid, "setgrouplimit",
					inVo.getLimit() == null ? null : inVo.getLimit().toString());
			qrcode = communityServiceImpl.getClientCodeFromImUrl(communityid, imuser);

		} catch (Exception e) {
			// e.printStackTrace();
		}

		jsonobj.put("data", community);
		jsonobj.put("qrcode", qrcode);
		if (sqlsuccess && urlsuccess) {
			jsonobj.put("message", "添加成功");
			jsonobj.put("result", "200");
		} else {
			jsonobj.put("message", "添加失败");
			jsonobj.put("result", "000");
		}

		// 新增加用户后，清除所有用户的授权缓存
		realm.clearAllUserAuthorazitionCache();

		// 调用接口
		// 返回json
		return jsonobj;

	}

	/**
	 * 移交群
	 *
	 * @param inVo
	 *            inVo
	 * @return json
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/transfercommunity", headers = "Accept=application/json;charset=UTF-8")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody
	public JSONObject transferCommunity(UpdateCommunityInVo inVo) throws Exception {
		System.err.println("transferCommunity-----" + inVo.getCommunityid() + "------" + inVo.getCommunityholder());
		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
		String roleName = currentLoginUser.getSysRole().getRoleName();
		SysCommunityResource community = null;
		boolean issuccess = false;

		JSONObject jsonobj = new JSONObject();

		if (StringUtils.isEmpty(inVo.getCommunityholder()) || StringUtils.isEmpty(inVo.getCommunityid())) {
			throw new CustomeBusinessJsonExciption("参数不全");
		}
		community = communityOperateService.getCommunityByIdAndIsDelete(inVo.getCommunityid(), false);
		if (roleName.equals("普通群主")) {
			if (!subject.isPermitted("community:update:" + inVo.getCommunityid())) {
				throw new CustomeBusinessJsonExciption("缺少操作群权限");
			}
		} else if (roleName.equals("管理员")) {
			if (community == null) {
				throw new CustomeBusinessJsonExciption("群不存在");
			} else {
				if (!subject.isPermitted("user:addcity:" + community.getCity())) {
					throw new CustomeBusinessJsonExciption("缺少城市权限");
				}
			}

		}

		// 获取群主用户名
		SysUserResource currentuser = sysUserOperateService.getUserByEmail(community.getCommunityHolder());

		SysUserResource sysuser = sysUserOperateService.getUserByEmail(inVo.getCommunityholder());
		System.out.println(
				"aaa-----" + sysuser + "------" + sysuser.getImUserName() + "-----" + sysuser.getSysUserRealName());
		if (sysuser == null) {
			throw new CustomeBusinessJsonExciption("被转让人非会员后台管理员");
		}
		// boolean islimitcity = true;
		// for (SysCity item : sysuser.getSysCities()) {
		// if (item.getSysCityId() == community.getCity()) {
		// islimitcity = false;
		// break;
		// }
		// }
		// if (islimitcity) {
		// throw new CustomeBusinessJsonExciption("被转让人非本城市管理员");
		// }
		jsonobj.put("communityid", inVo.getCommunityid());
		jsonobj.put("communityname", inVo.getCommunityname());

		boolean isadduser = communityServiceImpl.addOrExitCommunityUserFromImUrl(currentuser.getImUserName(),
				inVo.getCommunityid(), sysuser.getImUserName(), "add");
		System.out.println("bbb-----" + isadduser);
		if (!isadduser) {
			throw new CustomeBusinessJsonExciption("被转让人拉入群失败");
		}
		boolean isurlsuccess = communityServiceImpl.transferCommunityFromImUrl(currentuser.getImUserName(),
				inVo.getCommunityid(), sysuser.getImUserName());
		System.out.println("ccc-----" + isurlsuccess);
		if (isurlsuccess) {
			issuccess = communityOperateService.updateCommunityHolder(inVo.getCommunityid(), inVo.getCommunityholder());
		}

		// 新增加用户后，清除所有用户的授权缓存
		realm.clearAllUserAuthorazitionCache();
		jsonobj.put("communityid", community.getCommunityId());
		if (issuccess && isurlsuccess) {
			jsonobj.put("message", "修改成功");
			jsonobj.put("result", "200");
		} else {
			jsonobj.put("message", "修改失败");
			jsonobj.put("result", "000");
		}

		// 调用接口
		// 返回json
		return jsonobj;
	}

	@RequestMapping(value = "/updateCommunityName", headers = "Accept=application/json;charset=UTF-8")
	@ResponseBody()
	public JSONObject updateCommunityName(@RequestParam("communityid") String communityId,
			@RequestParam("communityname") String communityName) throws Exception {
		boolean isSuccess = communityOperateService.updateSomeParam(communityId, communityName, null);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("communityid", communityId);
		if (isSuccess) {
			jsonObject.put("message", "修改成功");
			jsonObject.put("result", "200");
		} else {
			jsonObject.put("message", "修改失败");
			jsonObject.put("result", "000");
		}
		return jsonObject;
	}

	@RequestMapping(value = "/hasEmail", headers = "Accept=application/json;charset=UTF-8")
	@ResponseBody()
	public JSONObject hasEmail(@RequestParam("email") String email) throws Exception {
		SysUserResource userResource = sysUserOperateService.getUserByEmail(email);
		boolean exists = userResource == null ? false : true;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("exists", exists);
		jsonObject.put("message", "查询成功");
		jsonObject.put("result", "200");
		return jsonObject;
	}

	@RequestMapping(value = "/sendMessage", headers = "Accept=application/json;charset=UTF-8")
	@ResponseBody()
	public JSONObject sendMessage(@RequestParam("communityids")String communityIds,@RequestParam("message")String message) throws Exception {
		String[] ids = communityIds.split(",");
		JSONObject jsonObject = new JSONObject();
		try {
			communityOperateService.sendMessage(ids, message);
			jsonObject.put("message", "发送成功");
			jsonObject.put("result", "200");
		} catch (Exception e) {
			jsonObject.put("message", e.getMessage());
			jsonObject.put("result", "000");
		}
		return jsonObject;
	}

	/**
	 * 查询添加成员
	 *
	 * @param name
	 *            name
	 * @return string
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/searchpassuser", headers = "Accept=application/json;charset=UTF-8")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody
	public String searchPassUser(String name) throws Exception {
		String passuser = "";

		if (StringUtils.isEmpty(name)) {
			return "";
		}
		boolean isMo = PhoneNOUtil.isMobileNO(name);
		if (isMo) {
			passuser = communityServiceImpl.getPassUserByMobile(name);
		} else {
			passuser = communityServiceImpl.getPassUser(name);
		}

		// 调用oa接口
		if (!StringUtils.isEmpty(passuser)) {
			passuser = "l:" + passuser;
		}

		// 调用接口
		// 返回json
		return passuser;
	}

	/**
	 *
	 * 修改群
	 *
	 * @param inVo
	 *            输入参数
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/updatecommunity", headers = "Accept=application/json;charset=UTF-8")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public JSONObject updateCommunity(UpdateCommunityInVo inVo) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
		String roleName = currentLoginUser.getSysRole().getRoleName();
		SysCommunityResource community = null;
		boolean issuccess = false;

		String communityName = "";
		if (inVo.getCategory() == null && StringUtils.isEmpty(inVo.getCommunityname())) {
			throw new CustomeBusinessJsonExciption("参数不全");
		}
		if (inVo.getCommunityname().length() < 2 || inVo.getCommunityname().length() > 10) {
			throw new CustomeBusinessJsonExciption("群名称为2到10位字符");
		}
		if (!StringUtils.isEmpty(inVo.getCommunityid())) {
			if (roleName.equals("普通群主")) {
				if (!subject.isPermitted("community:update:" + inVo.getCommunityid())) {
					throw new CustomeBusinessJsonExciption("缺少操作群权限");
				}
				community = communityOperateService.getCommunityByIdAndIsDelete(inVo.getCommunityid(), false);
			} else if (roleName.equals("管理员")) {
				community = communityOperateService.getCommunityByIdAndIsDelete(inVo.getCommunityid(), false);
				if (community == null) {
					throw new CustomeBusinessJsonExciption("群不存在");
				} else {
					if (!subject.isPermitted("user:addcity:" + community.getCity())) {
						throw new CustomeBusinessJsonExciption("缺少城市权限");
					}
				}

			} else {
				community = communityOperateService.getCommunityByIdAndIsDelete(inVo.getCommunityid(), false);
			}
		} else {
			throw new CustomeBusinessJsonExciption("参数不全");
		}
		if (community == null) {
			throw new CustomeBusinessJsonExciption("群不存在");
		}
		communityName = inVo.getCommunityname();
		boolean isurlsuccess = false;
		if (!StringUtils.isEmpty(communityName)) {
			SysUserResource sysuser = sysUserOperateService.getUserByEmail(community.getCommunityHolder());
			if (sysuser == null) {
				throw new CustomeBusinessJsonExciption("群主非会员后台管理员");
			}
			isurlsuccess = communityServiceImpl.updateCommunityFromImUrl(sysuser.getImUserName(),
					community.getCommunityId(), "modifygroup", communityName);
			if (isurlsuccess) {
				issuccess = communityOperateService.updateSomeParam(inVo.getCommunityid(), communityName,
						inVo.getCategory());
			}
		} else {
			isurlsuccess = true;
			issuccess = communityOperateService.updateSomeParam(inVo.getCommunityid(), "", inVo.getCategory());
		}

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("communityid", inVo.getCommunityid());
		if (issuccess && isurlsuccess) {
			jsonobj.put("message", "修改成功");
			jsonobj.put("result", "200");
		} else {
			jsonobj.put("message", "修改失败");
			jsonobj.put("result", "000");
		}

		// 调用接口
		// 返回json
		return jsonobj;

	}

	/**
	 *
	 * 删除群
	 *
	 * @param inVo
	 *            输入参数
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/delcommunity", headers = "Accept=application/json;charset=UTF-8")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public JSONObject delCommunity(CommunityManagerInVo inVo) throws Exception {

		System.err.println("delCommunity---------" + inVo.getCommunityid());

		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
		String roleName = currentLoginUser.getSysRole().getRoleName();
		SysCommunityResource community = null;
		boolean issuccess = false;
		StringBuffer errorbuf = new StringBuffer();

		if (!StringUtils.isEmpty(inVo.getCommunityid())) {
			String[] ids = inVo.getCommunityid().split(",");
			for (int i = 0; i < ids.length; i++) {
				community = communityOperateService.getCommunityByIdAndIsDelete(ids[i], false);
				if (community == null) {
					errorbuf.append(ids[i] + ",");
				} else {
					if (roleName.equals("普通群主")) {
						if (!currentLoginUser.getEmail().equals(community.getCommunityHolder())) {
							errorbuf.append(ids[i] + ",");
							break;
						}
					} else if (roleName.equals("管理员")) {
						if (subject.isPermitted("user:select:" + community.getCity())) {
							errorbuf.append(ids[i] + ",");
							break;
						}
					}
					// 调用接口
					String imuser = "";
					SysUserResource tempuser = sysUserOperateService.getUserByEmail(community.getCommunityHolder());
					if (tempuser != null) {
						imuser = tempuser.getImUserName();
					}
					boolean isurlsuccess = communityServiceImpl.delCommunityFromImUrl(imuser,
							community.getCommunityId());
					if (isurlsuccess) {
						issuccess = communityOperateService.updateIsdeleteCommunity(ids[i], true);
					} else {
						errorbuf.append(ids[i] + ",");
					}
				}
			}

		} else {
			throw new CustomeBusinessJsonExciption("参数不全");
		}
		String errorids = "";
		if (!StringUtils.isEmpty(errorbuf.toString())) {
			errorids = errorbuf.substring(0, errorbuf.length() - 1);
		}

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("communityid", errorids);
		if (issuccess) {
			jsonobj.put("message", "删除成功");
			jsonobj.put("result", "200");
		} else {
			jsonobj.put("message", "删除失败");
			jsonobj.put("result", "000");
		}

		// 调用接口
		// 返回json
		return jsonobj;
	}

	/**
	 *
	 * 恢复群
	 *
	 * @param inVo
	 *            输入参数
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/reccommunity", headers = "Accept=application/json;charset=UTF-8")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public JSONObject recCommunity(CommunityManagerInVo inVo) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) subject.getPrincipal();
		String roleName = currentLoginUser.getSysRole().getRoleName();
		SysCommunityResource community = null;
		boolean issuccess = false;
		StringBuffer errorbuf = new StringBuffer();

		if (!StringUtils.isEmpty(inVo.getCommunityid())) {
			String[] ids = inVo.getCommunityid().split(",");
			for (int i = 0; i < ids.length; i++) {
				community = communityOperateService.getCommunityByIdAndIsDelete(ids[i], true);
				if (community == null) {
					errorbuf.append(ids[i] + ",");
				} else {
					if (roleName.equals("普通群主")) {
						if (!currentLoginUser.getEmail().equals(community.getCommunityHolder())) {
							errorbuf.append(ids[i] + ",");
							break;
						}
					} else if (roleName.equals("管理员")) {
						if (subject.isPermitted("user:select:" + community.getCity())) {
							errorbuf.append(ids[i] + ",");
							break;
						}
					}
					// 调用接口
					String imuser = "";
					SysUserResource tempuser = sysUserOperateService.getUserByEmail(community.getCommunityHolder());
					if (tempuser != null) {
						imuser = tempuser.getImUserName();
					}
					boolean isurlsuccess = communityServiceImpl.recCommunityFromImUrl(imuser,
							community.getCommunityId());
					if (isurlsuccess) {
						issuccess = communityOperateService.updateIsdeleteCommunity(ids[i], false);
					} else {
						errorbuf.append(ids[i] + ",");
					}
				}
			}

		} else {
			throw new CustomeBusinessJsonExciption("参数不全");
		}
		String errorids = "";
		if (!StringUtils.isEmpty(errorbuf.toString())) {
			errorids = errorbuf.substring(0, errorbuf.length() - 1);
		}

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("communityid", errorids);
		if (issuccess) {
			jsonobj.put("message", "恢复成功");
			jsonobj.put("result", "200");
		} else {
			jsonobj.put("message", "恢复失败");
			jsonobj.put("result", "000");
		}

		// 调用接口
		// 返回json
		return jsonobj;
	}

	/**
	 * 获取im联系人
	 *
	 * @return im,nickname
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/getimuser")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public List<String> getImUser() throws Exception {
		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) subject.getPrincipal();
		List<String> user = new ArrayList<String>();
		SysUserResource sysuser = sysUserOperateService.getUserByEmail(currentLoginUser.getEmail());
		if (sysuser == null) {
			throw new CustomeBusinessJsonExciption("用户不存在");
		}
		user = communityServiceImpl.getRecentContactsFromImUrl(sysuser.getImUserName(), "clientlg,xf,agent,agent_esf");

		// 调用接口
		// 返回json
		return user;
	}

	/**
	 * 获取二维码
	 *
	 * @param communityid
	 *            群id
	 * @param communityholder
	 *            群主
	 * @return 二维码
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/getqrcodebyid")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public String getQrCode(String communityid, String communityholder) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) subject.getPrincipal();
		SysUserResource sysuser = sysUserOperateService.getUserByEmail(currentLoginUser.getEmail());
		if (sysuser == null) {
			throw new CustomeBusinessJsonExciption("用户不存在");
		}
		String qrcode = "";
		qrcode = communityServiceImpl.getClientCodeFromImUrl(communityid, communityholder);
		// 调用接口
		// 返回json
		return qrcode;
	}

	/**
	 * 添加单个成员
	 *
	 * @param communityholder
	 *            群主
	 * @param communityid
	 *            成员id
	 * @param imuser
	 *            im用户名
	 * @return json
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/singleaddser", headers = "Accept=application/json;charset=UTF-8")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public JSONObject singleaddUser(String communityholder, String communityid, String imuser) throws Exception {
		if (StringUtils.isEmpty(communityholder) || communityid == null || StringUtils.isEmpty(imuser)) {
			throw new CustomeBusinessJsonExciption("参数不全");
		}
		SysCommunityResource community = communityOperateService.getCommunityById(communityid);
		if (community == null) {
			throw new CustomeBusinessJsonExciption("群不存在");
		}
		SysUserResource sysuser = sysUserOperateService.getUserByEmail(community.getCommunityHolder());
		if (sysuser == null) {
			throw new CustomeBusinessJsonExciption("用户不存在");
		}
		boolean issuccess = communityServiceImpl.addOrExitCommunityUserFromImUrl(sysuser.getImUserName(), communityid,
				imuser, "add");

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("communityid", communityid);
		if (issuccess) {
			jsonobj.put("message", "添加成功");
			jsonobj.put("result", "200");
		} else {
			jsonobj.put("message", "添加失败");
			jsonobj.put("result", "000");
		}

		// 调用接口
		// 返回json
		return jsonobj;
	}

	/**
	 * 获取可转让用户
	 *
	 * @param pageindex
	 *            页码
	 * @param pagesize
	 *            页大小
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/getlocaluser")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public List<String> getLocalUser(Integer pageindex, Integer pagesize) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) subject.getPrincipal();
		List<SysUserResource> community = new ArrayList<SysUserResource>();
		String roleName = currentLoginUser.getSysRole().getRoleName();
		List<String> user = new ArrayList<String>();

		Integer pageSize = 100;
		Integer pageIndex = 1;

		OutParameterUtils<Integer> total = new OutParameterUtils<Integer>();

		if (pageindex != null && pageindex > 0) {
			pageIndex = pageindex;
		}
		if (pagesize != null && pagesize > 0) {
			pageSize = pagesize;
		}

		List<Integer> cityIds = new ArrayList<Integer>();

		if (roleName.equals("超级管理员")) {
			community = sysUserOperateService.getPageUserByRolesAndCitiesAndEmail(null, null, "", pageIndex, pageSize,
					total);
		} else if (roleName.equals("管理员")) {
			Set<SysCity> cities = currentLoginUser.getSysCities();
			for (SysCity city : cities) {
				cityIds.add(city.getSysCityId());
			}
			community = sysUserOperateService.getPageUserByRolesAndCitiesAndEmail(null, cityIds, "", pageIndex,
					pageSize, total);
			cityIds.clear();
		} else {
			Set<SysCity> cities = currentLoginUser.getSysCities();
			for (SysCity city : cities) {
				cityIds.add(city.getSysCityId());
			}
			community = sysUserOperateService.getPageUserByRolesAndCitiesAndEmail(null, cityIds, "", pageIndex,
					pageSize, total);
			cityIds.clear();
		}

		if (community != null && community.size() > 0) {
			for (int i = 0; i < community.size(); i++) {
				user.add(community.get(i).getSysUserRealName() + "(" + community.get(i).getEmail() + ")");
			}
		}

		// 调用接口
		// 返回json
		return user;
	}

	/**
	 * 查询后台成员
	 *
	 * @param cityid
	 *            cityid
	 * @param name
	 *            name
	 * @return List<String> 真实姓名(邮箱)
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/searchuser")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public List<String> searchUser(Integer cityid, String name) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		SysUserResource currentLoginUser = (SysUserResource) subject.getPrincipal();
		List<SysUserResource> sysusers = new ArrayList<SysUserResource>();
		String roleName = currentLoginUser.getSysRole().getRoleName();
		List<String> user = new ArrayList<String>();
		boolean islimitcity = true;

		if (cityid == null || StringUtils.isEmpty(name)) {
			return null;
		}

		if (roleName.equals("管理员")) {
			if (subject.isPermitted("user:update:" + cityid)) {
				islimitcity = false;
			}
		} else if (roleName.equals("普通群主")) {
			Set<SysCity> cities = currentLoginUser.getSysCities();
			for (SysCity city : cities) {
				if (cityid == city.getSysCityId()) {
					islimitcity = false;
				}
			}

		} else if (roleName.equals("超级管理员")) {
			islimitcity = false;
		}
		if (!islimitcity) {
			sysusers = sysUserOperateService.getUserLikeEmailOrName(name);
		}

		if (sysusers != null && sysusers.size() > 0) {
			for (int i = 0; i < sysusers.size(); i++) {
				user.add(sysusers.get(i).getSysUserRealName() + "(" + sysusers.get(i).getEmail() + ")");
			}
		}

		// 调用接口
		// 返回json
		return user;
	}

	/**
	 * 调用链接获取outmodel
	 *
	 * @param communitylists
	 *            本地数据
	 * @return outmodel
	 * @throws Exception
	 *             异常
	 */
	private List<CommunityManagerOutVo> getOutModel(List<SysCommunityResource> communitylists) throws Exception {
		Map<String, ImUserServiceInterfaceUrlOut> imMap = new HashMap<String, ImUserServiceInterfaceUrlOut>();
		List<CommunityManagerOutVo> outmodel = new ArrayList<CommunityManagerOutVo>();
		List<String> imurllist = new ArrayList<String>();
		// 调接口
		for (SysCommunityResource item : communitylists) {
			imurllist.add(item.getCommunityId());
		}
		try {
			imMap = communityServiceImpl.getCommunityListByIdFromImUrl(imurllist);
		} catch (Exception e) {
			imMap = null;
		}

		if (imMap != null && imMap.size() > 0) {
			for (SysCommunityResource item : communitylists) {
				CommunityManagerOutVo outitem = new CommunityManagerOutVo();
				ImUserServiceInterfaceUrlOut imitem = imMap.get(item.getCommunityId());
				outitem.setCategoryid(item.getCategory());
				String category = "";
				SysCommunityCategory catgitem = sysCommunityCategoryService.getCategoryById(item.getCategory());
				if (catgitem != null) {
					category = catgitem.getCategoryName();
				}
				outitem.setCategoryname(category);
				outitem.setCommunityid(item.getCommunityId());
				outitem.setCommunityholder(item.getCommunityHolder());
				outitem.setCommunityname(item.getCommunityName());
				outitem.setCityid(item.getCity());
				outitem.setCommunityLimit(item.getCommunityLimit());
				outitem.setProjectid(item.getProjectid());
				outitem.setCommunityNotice(item.getCommunityNotice());
				outitem.setCommunityPic(item.getCommunityPic());
				SysCity cityitem = sysCityOperateService.getByCityId(item.getCity());
				SysRegion region = regionOperateService.getSysRegionById(cityitem.getSysregionid());
				outitem.setRegionName(region.getRegionName());
				outitem.setCityname(cityitem.getCityName());
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				outitem.setCreatetime(sdf.format(item.getCreateTime()));
				String qrcode = "";
				String qrurl = "";

				if (imitem != null) {
					// 获取分类名称
					String lasttimeitem = imitem.getLastmessage();
					if (!StringUtils.isEmpty(lasttimeitem)) {
						lasttimeitem = lasttimeitem.substring(0, 10).replace("-", "/");
						imitem.setLastmessage(lasttimeitem);
					}
					// try {
					// String result =
					// communityServiceImpl.getClientCodeFromImUrl(item.getCommunityId(),
					// imitem == null ? "" : imitem.getMaster());
					// qrcode = result.split(",")[0];
					// qrurl = result.split(",")[1];
					// } catch (Exception e) {
					// // e.printStackTrace();
					// }

					outitem.setImurlout(imitem);
				} else {
					outitem.setImurlout(new ImUserServiceInterfaceUrlOut());
				}
				outitem.setQrcode(qrcode);
				outitem.setQrurl(qrurl);
				// url编码后的qrurl
				outitem.setQrurlencode(URLEncoder.encode(qrurl));
				outmodel.add(outitem);
			}
		} else {
			for (SysCommunityResource item : communitylists) {
				CommunityManagerOutVo outitem = new CommunityManagerOutVo();
				outitem.setCategoryid(item.getCategory());
				String category = "";
				SysCommunityCategory catgitem = sysCommunityCategoryService.getCategoryById(item.getCategory());
				if (catgitem != null) {
					category = catgitem.getCategoryName();
				}
				outitem.setCategoryname(category);
				outitem.setCommunityid(item.getCommunityId());
				outitem.setCommunityholder(item.getCommunityHolder());
				outitem.setCommunityname(item.getCommunityName());
				outitem.setCityid(item.getCity());
				SysCity cityitem = sysCityOperateService.getByCityId(item.getCity());
				outitem.setCityname(cityitem.getCityName());
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				outitem.setCreatetime(sdf.format(item.getCreateTime()));
				outitem.setQrcode("");
				outitem.setQrurl("");
				outitem.setQrurlencode("");
				outitem.setImurlout(new ImUserServiceInterfaceUrlOut());
				outmodel.add(outitem);
			}
		}
		return outmodel;

	}

	/**
	 * 上传图片
	 *
	 * @param request
	 *            请求
	 * @return JSONObject
	 *
	 */
	@RequestMapping(value = "/uploadqunpic", method = RequestMethod.POST)
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	@ResponseBody()
	public JSONObject upLoadImage(HttpServletRequest request) {
		HashMap<String, String> params = null;
		// 校验
		params = ImgUpLoadUtils.uploadImg(request);
		JSONObject result = new JSONObject();
		if (params != null && params.size() > 0) {
			result.put("result", "200");
			result.put("message", "");
			result.put("data", params);
			// System.out.println("registerimg:" + result);
		} else {
			result.put("result", "000");
			result.put("message", "上传图片失败");
			result.put("data", "");
			// System.out.println("registerimgfail:" + result);
		}
		return result;
	}

	/**
	 * 群发消息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/groupmessage.html")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	public String groupMessage() throws Exception {
		return "/views/groupmessage";
	}

	/**
	 * 群发消息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pagegroup.html")
	@RequiresRoles(value = { "超级管理员", "管理员", "普通群主" }, logical = Logical.OR)
	public String getgroupPageMessage(Model model, CommunityManagerInVo inVo) throws Exception {
		SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
		String roleName = currentLoginUser.getSysRole().getRoleName();

		List<SysCommunityResource> communitylists = new ArrayList<SysCommunityResource>();
		List<CommunityManagerOutVo> outmodel = new ArrayList<CommunityManagerOutVo>();
		Integer pageSize = 20;
		Integer pageIndex = 1;
		Long totalcount = 0L;
		boolean isdelete = false;
		if (inVo.getPagesize() != null || inVo.getPagesize() != null) {
			if (inVo.getPagesize() > 150 || inVo.getPagesize() < 1) {
				throw new CustomeBusinessJsonExciption("页码输入有误");
			}
			pageSize = inVo.getPagesize();
			pageIndex = inVo.getPageindex();
		}
		if (inVo.getIsdelete() != null && inVo.getIsdelete() == 1) {
			isdelete = true;
		}

		if (roleName.equals("超级管理员")) {
			// 查询全部
			// totalcount =
			// communityOperateService.getCountLikeNameByParam(inVo.getCommunityname(),
			// null, null, isdelete);
			// inVo.setCommunityid("10039165");
			// System.err.println("a-------------------------------" +
			// inVo.getProjectid()+"---"+inVo.getCityId()+"----"+inVo.getCategory()+"---"+inVo.getRegionId()+"---"+inVo.getCommunityid()+"---"+inVo.getCommunityname());
			totalcount = communityOperateService.searchCommunityCount(inVo.getProjectid(), inVo.getCategory(),
					inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(), isdelete);
			if (totalcount != null && totalcount > 0) {
				// communitylists =
				// communityOperateService.getCommunityLikeNameByParam(inVo.getCommunityname(),
				// pageIndex,
				// pageSize, inVo.getOrderstr(), null, null, isdelete);
				communitylists = communityOperateService.searchCommunity(inVo.getProjectid(), inVo.getCategory(),
						inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(),
						pageIndex, pageSize, isdelete);
			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}

		} else if (roleName.equals("管理员")) {
			Set<SysCity> cities = currentLoginUser.getSysCities();
			Collection<Integer> cityIds = new ArrayList<Integer>();
			for (SysCity city : cities) {
				cityIds.add(city.getSysCityId());
			}

			// 查询全部
			// totalcount =
			// communityOperateService.getCountLikeNameByParam(inVo.getCommunityname(),
			// null, cityIds,
			// isdelete);
			totalcount = communityOperateService.searchCommunityCount(inVo.getProjectid(), inVo.getCategory(),
					inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(), isdelete);
			if (totalcount != null && totalcount > 0) {
				// communitylists =
				// communityOperateService.getCommunityLikeNameByParam(inVo.getCommunityname(),
				// pageIndex,
				// pageSize, inVo.getOrderstr(), null, cityIds, isdelete);
				communitylists = communityOperateService.searchCommunity(inVo.getProjectid(), inVo.getCategory(),
						inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(), pageIndex,
						pageSize, isdelete);
			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}
		} else {
			Set<SysCity> cities = currentLoginUser.getSysCities();
			Collection<Integer> cityIds = new ArrayList<Integer>();
			for (SysCity city : cities) {
				cityIds.add(city.getSysCityId());
			}
			// 查询全部
			// totalcount =
			// communityOperateService.getCountLikeNameByParam(inVo.getCommunityname(),
			// currentLoginUser.getEmail(), cityIds, isdelete);
			totalcount = communityOperateService.searchCommunityCount(inVo.getProjectid(), inVo.getCategory(),
					inVo.getCityId(), inVo.getRegionId(), inVo.getCommunityid(), inVo.getCommunityname(), isdelete);
			if (totalcount != null && totalcount > 0) {
				// communitylists =
				// communityOperateService.getCommunityLikeNameByParam(inVo.getCommunityname(),
				// pageIndex,
				// pageSize, inVo.getOrderstr(), currentLoginUser.getEmail(),
				// cityIds, isdelete);

			}
			if (communitylists != null && communitylists.size() > 0) {
				outmodel = getOutModel(communitylists);
			}
		}
		// 正常的表
		model.addAttribute("totalcount", totalcount);
		model.addAttribute("totalpage", totalcount / pageSize + 1);
		model.addAttribute("pageindex", pageIndex);
		model.addAttribute("communitylists", outmodel);
		model.addAttribute("isdelete", isdelete == true ? 1 : 0);
		model.addAttribute("cities", currentLoginUser.getSysCities());
		model.addAttribute("searchtext", inVo.getCommunityname());
		// 删除的表

		// 调用接口
		return "/views/grouplist";
	}

}
