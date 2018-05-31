/**
 * File：MemberManageController.java
 * Package：com.fang.im.management.web.controller
 * Author：Administrator
 * Date：2017年4月20日 上午10:05:07
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.web.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fang.im.management.po.SysCity;
import com.fang.im.management.po.SysCommunityResource;
import com.fang.im.management.po.SysRole;
import com.fang.im.management.po.SysUserResource;
import com.fang.im.management.service.CommunityOperateService;
import com.fang.im.management.service.SysCityOperateService;
import com.fang.im.management.service.SysUserOperateService;
import com.fang.im.management.systemexception.CustomeBusinessJsonExciption;
import com.fang.im.management.utils.ImMd5ForMember;
import com.fang.im.management.utils.MD5Util;
import com.fang.im.management.utils.PhoneNOUtil;
import com.fang.im.management.utils.PropertiesUtils;
import com.fang.im.management.utils.SlJson;
import com.fang.im.management.utils.SlMd5;
import com.fang.im.management.utils.SlRef;
import com.fang.im.management.utils.SlRequest;
import com.fang.im.management.web.vo.AddMembersIn;
import com.fang.im.management.web.vo.AutoAddExitGroupModel;
import com.fang.im.management.web.vo.BlackMemberInVo;
import com.fang.im.management.web.vo.CommonResultJson;
import com.fang.im.management.web.vo.ForbiddenMemberInVo;
import com.fang.im.management.web.vo.GetCommunitiesInVo;
import com.fang.im.management.web.vo.GetGroupInfoModel;
import com.fang.im.management.web.vo.GetGroupInfoRes;
import com.fang.im.management.web.vo.GetGroupUserListModel;
import com.fang.im.management.web.vo.GetGroupUserListRes;
import com.fang.im.management.web.vo.GetRecentContactsModel;
import com.fang.im.management.web.vo.GetRecentContactsRes;
import com.fang.im.management.web.vo.MemberGetPageListInVo;
import com.fang.im.management.web.vo.ModifyGroupCardNameModel;
import com.fang.im.management.web.vo.SearchMemberInVo;
import com.fang.im.management.web.vo.SearchMemberPassportOutVo;
import com.fang.im.management.web.vo.SysCommunityResourceOutVo;
import com.fang.im.management.web.vo.UnBlackMemberInVo;
import com.fang.im.management.web.vo.UnForbiddenMemberInVo;
import com.fang.im.management.web.vo.UpdateCardnameInVo;
import com.fang.im.management.web.vo.out.ChangeGroupOutVo;
import com.fang.im.management.web.vo.out.GetCommunitiesOutVo;

/**
 * MemberManageController
 *
 * @author ldb
 *
 */
@Controller
public class MemberManageController {

  /**
   * 城市信息操作
   */
  @Autowired
  private SysCityOperateService sysCityOperateService;

  /**
   * 群操作
   */
  @Autowired
  private CommunityOperateService communityOperateService;

  /**
   * sysUserOperateService
   */
  @Autowired
  private SysUserOperateService sysUserOperateService;

  /**
   * 成员管理首页
   *
   * @param model
   *        model
   * @return
   *         跳转至页面
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/member.html")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public String memberManagePage(Model model) throws Exception {

    /*AddMembersIn aa = new AddMembersIn();
    aa.setAction("add");
    aa.setForm("im:system");
    aa.setGroupid("10039231");
    aa.setMembers("(0030odfb,00320882,0032�ͷ�,003448,0035�ѷ�,003515,0036584913,00390e,00393lod,003bz7,003qq,003yangyang,00408209,0042jack)");
    addMembers(aa);*/

    return "views/member";
  }

  /**
   * 获得群成员列表
   *
   * @param inModel
   *        inModel
   * @return
   *         返回json数据
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/getPageList.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody GetGroupUserListRes getPageList(MemberGetPageListInVo inModel) throws Exception {
    String inter = PropertiesUtils.getPropertyValue("getGroupUserListInterface");
    inter = inter.replace("{0}", inModel.getGroupid());
    inter = inter.replace("{1}", inModel.getPageindex());
    inter = inter.replace("{2}", inModel.getPagesize());
    String ordercolumn = inModel.getOrdercolumn();
    String ordermethod = inModel.getOrdermethod();
    if (StringUtils.isEmpty(ordercolumn)) {
      inter = inter.substring(0, inter.length() - 3);
    } else {
      inter = inter.replace("{3}", ordercolumn + "=" + ordermethod);
    }

    String res = SlRequest.getHtml(inter);

    if (StringUtils.isEmpty(res)) {
      return null;
    }
    Map<String, Class<?>> maps = new HashMap<String, Class<?>>();
    maps.put("data", GetGroupUserListModel.class);
    GetGroupUserListRes resJson = SlJson.toBean(res, GetGroupUserListRes.class, maps);
    for (GetGroupUserListModel model : resJson.getData()) {
      String imname = model.getUsername();
      String picurl = getPicByImname(imname);
      model.setPicurl(picurl);
    }

    return resJson;
  }

  /**
   * 获取当前登录用户可管理的用户群
   *
   * @param model
   *        model
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/getCommunities.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody GetCommunitiesOutVo getCommunities(GetCommunitiesInVo model) throws Exception {
    List<SysCommunityResource> sysCommunityResource = null;
    SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
    SysRole sysRole = currentLoginUser.getSysRole();
    Integer pageindex = model.getPageindex();
    Integer pagesize = model.getPagesize();
    if (pageindex == null || pageindex < 1) {
      pageindex = 1;
    }
    if (pagesize == null || pagesize < 1) {
      pagesize = 6;
    }
    SlRef<Integer> count = new SlRef<Integer>(0);
    if (sysRole.getRoleName().equals("超级管理员")) {
      // 超级管理员，可以管理所有群
      Set<Integer> cityIds = sysCityOperateService.getAllCities().keySet();
      sysCommunityResource = communityOperateService.getCommunityByCitiesOrderByCreatetimePaging(cityIds, pageindex, pagesize, count);
    } else if (sysRole.getRoleName().equals("管理员")) {
      // 管理员，只能管理该管理员所拥有城市的群
      Set<SysCity> cities = currentLoginUser.getSysCities();
      Collection<Integer> cityIds = new ArrayList<Integer>();
      for (SysCity city : cities) {
        cityIds.add(city.getSysCityId());
      }
      sysCommunityResource = communityOperateService.getCommunityByCitiesOrderByCreatetimePaging(cityIds, pageindex, pagesize, count);
    } else {
      // 普通群主，只能管理自己的群
      String email = currentLoginUser.getEmail();
      if (!StringUtils.isEmpty(email)) {
        email = email.replace("@fang.com", "");
      }
      sysCommunityResource = communityOperateService.getCommunityByUserOrderByCreatetimePaging(email, pageindex, pagesize, count);
    }
    if (sysCommunityResource == null || sysCommunityResource.size() < 1) {
      throw new CustomeBusinessJsonExciption("没有可管理的群");
    }

    List<SysCommunityResourceOutVo> sysCommunityResourceOutVos = new ArrayList<SysCommunityResourceOutVo>();

    String inter = PropertiesUtils.getPropertyValue("getGroupInfoInterface");
    for (int i = 0; i < sysCommunityResource.size(); i++) {
      SysCommunityResourceOutVo vo = new SysCommunityResourceOutVo();
      BeanUtils.copyProperties(vo, sysCommunityResource.get(i));
      String cid = sysCommunityResource.get(i).getCommunityId();
      String reinter = inter.replace("{0}", cid);
      String res = SlRequest.getHtml(reinter);
      Map<String, Class<?>> maps = new HashMap<String, Class<?>>();
      maps.put("data", GetGroupInfoModel.class);
      GetGroupInfoRes bean = SlJson.toBean(res, GetGroupInfoRes.class, maps);
      String pic = "";
      if (bean != null && bean.getData() != null && bean.getData().size() > 0 && bean.getData().get(0).getPic() != null && !bean.getData().get(0).getPic().equals("\"null\"")) {
        pic = bean.getData().get(0).getPic();
      }
      vo.setPic(pic);
      sysCommunityResourceOutVos.add(vo);
    }

    GetCommunitiesOutVo vo = new GetCommunitiesOutVo();
    vo.setTotal((count.get()));
    vo.setPageindex(pageindex);
    vo.setPagesize(pagesize);
    vo.setData(sysCommunityResourceOutVos);

    // 原始使用代码分页，已替换
    /*if (sysCommunityResource.size() <= 6) {
      vo.setData(sysCommunityResource);
      return vo;
    }
    List<SysCommunityResource> datas = new ArrayList<SysCommunityResource>();
    for (int i = (pageindex - 1) * pagesize + 1, j = 0; j < pagesize && i <= sysCommunityResource.size(); i++, j++) {
      datas.add(sysCommunityResource.get(i - 1));
    }
    vo.setData(datas);*/

    return vo;
  }

  /**
   * getNearestCommunity
   *
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/getNearestCommunity.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody GetGroupInfoRes getNearestCommunity() throws Exception {
    SysCommunityResource resCommunity;
    SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
    SysRole sysRole = currentLoginUser.getSysRole();
    if (sysRole.getRoleName().equals("超级管理员")) {
      // 超级管理员，可以管理所有群
      Set<Integer> cityIds = sysCityOperateService.getAllCities().keySet();
      resCommunity = communityOperateService.getNearestCommunityByCitiesOrderByCreatetime(cityIds);
    } else if (sysRole.getRoleName().equals("管理员")) {
      // 管理员，只能管理该管理员所拥有城市的群
      Set<SysCity> cities = currentLoginUser.getSysCities();
      Collection<Integer> cityIds = new ArrayList<Integer>();
      for (SysCity city : cities) {
        cityIds.add(city.getSysCityId());
      }
      resCommunity = communityOperateService.getNearestCommunityByCitiesOrderByCreatetime(cityIds);
    } else {
      // 普通群主，只能管理自己的群
      String email = currentLoginUser.getEmail();
      if (!StringUtils.isEmpty(email)) {
        email = email.replace("@fang.com", "");
      }
      resCommunity = communityOperateService.getNearestCommunityByUserOrderByCreatetime(email);
    }
    if (resCommunity == null) {
      throw new CustomeBusinessJsonExciption("没有可管理的群");
    }

    String inter = PropertiesUtils.getPropertyValue("getGroupInfoInterface");
    inter = inter.replace("{0}", resCommunity.getCommunityId());
    String res = SlRequest.getHtml(inter);
    Map<String, Class<?>> maps = new HashMap<String, Class<?>>();
    maps.put("data", GetGroupInfoModel.class);
    GetGroupInfoRes bean = SlJson.toBean(res, GetGroupInfoRes.class, maps);

    return bean;
  }

  /**
   * 切换群操作
   *
   * @param groupid
   *        群ID
   * @return
   *         返回json数据
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/changeGroup.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody ChangeGroupOutVo changeGroup(String groupid) throws Exception {
    String inter = PropertiesUtils.getPropertyValue("getGroupInfoInterface");
    inter = inter.replace("{0}", groupid);
    String res = SlRequest.getHtml(inter);
    Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
    classMap.put("data", GetGroupInfoModel.class);
    GetGroupInfoRes resObject = SlJson.toBean(res, GetGroupInfoRes.class, classMap);
    if (resObject == null || resObject.getData() == null || resObject.getData().size() < 1) {
      return null;
    }
    GetGroupInfoModel getGroupInfoModel = resObject.getData().get(0);
    ChangeGroupOutVo vo = new ChangeGroupOutVo();

    vo.setGroupid(getGroupInfoModel.getGroupid());
    vo.setGroupname(getGroupInfoModel.getGroupname());
    vo.setCount(getGroupInfoModel.getCount());
    vo.setLimit(getGroupInfoModel.getLimit());
    vo.setPic(getGroupInfoModel.getPic());
    return vo;
  }

  /**
   * 获得最近联系人
   *
   * @param imname
   *        imname
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/getRecentMember.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody GetRecentContactsRes getRecentMember(String imname) throws Exception {
    SysUserResource currentLoginUser = (SysUserResource) SecurityUtils.getSubject().getPrincipal();
    imname = currentLoginUser.getImUserName();
    String inter = PropertiesUtils.getPropertyValue("getRecentContactsInterface");
    inter = inter.replace("{0}", imname);
    String res = SlRequest.getHtml(inter);
    Map<String, Class<?>> maps = new HashMap<String, Class<?>>();
    maps.put("data", GetRecentContactsModel.class);
    GetRecentContactsRes bean = SlJson.toBean(res, GetRecentContactsRes.class, maps);
    if (bean == null || bean.getData() == null || bean.getData().size() < 1) {
      return null;
    } else {
      for (GetRecentContactsModel model : bean.getData()) {
        String imusername = model.getImusername();
        String url = getPicByImname(imusername);
        model.setPicurl(url);
      }
      return bean;
    }
  }

  /**
   * searchMemberPassport
   *
   * @param key
   *        key
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/searchMemberPassport.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody SearchMemberPassportOutVo searchMemberPassport(String key) throws Exception {
    if (StringUtils.isEmpty(key)) {
      return null;
    }
    String res = "";
    String name = "";
    boolean isMo = PhoneNOUtil.isMobileNO(key);
    if (isMo) {
      String inter = PropertiesUtils.getPropertyValue("interface_mobile_info_2011Interface");
      inter = inter.replace("{0}", key);
      res = SlRequest.getHtml(inter);
      int index = res.indexOf("<return_result>");
      String num = res.substring(index + 15, index + 18);
      if (num.equals("100")) {
        int begin = res.indexOf("<username>");
        int end = res.indexOf("</username>");
        name = res.substring(begin + 19, end - 3);
      }
    } else {
      String gkkey = URLEncoder.encode(key, "gb2312");
      String inter = PropertiesUtils.getPropertyValue("interface_user_info_2011Interface");
      inter = inter.replace("{0}", gkkey);
      res = SlRequest.getHtml(inter);
      int index = res.indexOf("<return_result>");
      String num = res.substring(index + 15, index + 18);
      if (num.equals("100")) {
        name = key;
      }
    }

    if (StringUtils.isEmpty(name)) {
      return null;
    }
    String url = getPicByImname("l:" + name);

    SearchMemberPassportOutVo vo = new SearchMemberPassportOutVo();
    vo.setName(name);
    vo.setPic(url);

    return vo;
  }

  /**
   * 通过关键字搜索群成员
   *
   * @param model
   *        model
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/searchMember.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody GetGroupUserListRes searchMember(SearchMemberInVo model) throws Exception {

    String groupid = model.getGroupid();
    if (StringUtils.isEmpty(groupid)) {
      return null;
    }
    String inter = PropertiesUtils.getPropertyValue("getGroupUserInfoInterface");
    inter = inter.replace("{0}", groupid);
    String str1, str2, str3, str4, str5, str6, str7, str8, str9, str10;
    if (!StringUtils.isEmpty(model.getUsername())) {
      str1 = model.getUsername();
    } else {
      str1 = "";
    }
    if (!StringUtils.isEmpty(model.getAddstarttime())) {
      str2 = model.getAddstarttime();
    } else {
      str2 = "";
    }
    if (!StringUtils.isEmpty(model.getAddendtime())) {
      str3 = model.getAddendtime();
    } else {
      str3 = "";
    }
    if (!StringUtils.isEmpty(model.getLastmessagestarttime())) {
      str4 = model.getLastmessagestarttime();
    } else {
      str4 = "";
    }
    if (!StringUtils.isEmpty(model.getLastmessageendtime())) {
      str5 = model.getLastmessageendtime();
    } else {
      str5 = "";
    }
    if (!StringUtils.isEmpty(model.getPageindex())) {
      str6 = model.getPageindex();
    } else {
      str6 = "";
    }
    if (!StringUtils.isEmpty(model.getPagesize())) {
      str7 = model.getPagesize();
    } else {
      str7 = "";
    }
    if (!StringUtils.isEmpty(model.getForbidden())) {
      str8 = model.getForbidden();
    } else {
      str8 = "";
    }
    if (!StringUtils.isEmpty(model.getBlack())) {
      str9 = model.getBlack();
    } else {
      str9 = "";
    }
    if (StringUtils.isEmpty(model.getOrdercolumn())) {
      str10 = "";
    } else {
      str10 = model.getOrdercolumn();
      if (!StringUtils.isEmpty(model.getOrdermethod())) {
        str10 += "=" + model.getOrdermethod();
      }
    }

    inter = inter.replace("{1}", str1).replace("{2}", str2).replace("{3}", str3).replace("{4}", str4).replace("{5}", str5).replace("{6}", str6).replace("{7}", str7)
        .replace("{8}", str8).replace("{9}", str9)
        .replace("{10}", str10);

    String res = SlRequest.getHtml(inter);
    Map<String, Class<?>> maps = new HashMap<String, Class<?>>();
    maps.put("data", GetGroupUserListModel.class);
    GetGroupUserListRes bean = SlJson.toBean(res, GetGroupUserListRes.class, maps);
    for (GetGroupUserListModel xiia : bean.getData()) {
      String imname = xiia.getUsername();
      xiia.setPicurl(getPicByImname(imname));
    }

    return bean;
  }

  /**
   * getMemberById
   *
   * @param memberid
   *        memberid
   * @param groupid
   *        groupid
   * @return
   *         return
   */
  @RequestMapping("/getMemberById.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody GetGroupUserListModel getMemberById(String memberid, String groupid) {
    if (StringUtils.isEmpty(memberid) || StringUtils.isEmpty(groupid)) {
      return null;
    }
    String inter = PropertiesUtils.getPropertyValue("getGroupUserInfoInterface");
    inter = inter.replace("{0}", groupid).replace("{1}", memberid).replace("{2}", "").replace("{3}", "").replace("{4}", "").replace("{5}", "").replace("{6}", "")
        .replace("{7}", "").replace("{8}", "").replace("{9}", "").replace("{10}", "");
    String res = SlRequest.getHtml(inter);
    Map<String, Class<?>> maps = new HashMap<String, Class<?>>();
    maps.put("data", GetGroupUserListModel.class);
    GetGroupUserListRes bean = SlJson.toBean(res, GetGroupUserListRes.class, maps);
    if (bean == null || bean.getData() == null || bean.getData().size() < 1) {
      return null;
    }
    return bean.getData().get(0);
  }

  /**
   * searchCommunity
   *
   * @param key
   *        key
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/searchCommunity.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody List<SysCommunityResource> searchCommunity(String key) throws Exception {
    if (key == null || key.equals("")) {
      return null;
    }
    List<SysCommunityResource> res = new ArrayList<SysCommunityResource>();
    SysCommunityResource communityById = communityOperateService.getCommunityById(key);
    if (communityById != null) {
      res.add(communityById);
    }
    List<SysCommunityResource> communitiesByName = communityOperateService.getCommunitiesByName(key);
    if (communitiesByName != null && communitiesByName.size() > 0) {
      res.addAll(communitiesByName);
    }
    if (res.size() < 1) {
      return null;
    }
    return res;
  }

  /**
   * addMembers
   *
   * @param model
   *        model
   * @return
   *         return
   */
  @RequestMapping("/addMembers.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody String addMembers(AddMembersIn model) {
    String form = model.getForm();
    String action = model.getAction();
    String members = model.getMembers();
    String groupid = model.getGroupid();
    if (StringUtils.isEmpty(form) || StringUtils.isEmpty(action) || StringUtils.isEmpty(members) || StringUtils.isEmpty(groupid)) {
      return "error";
    }
    String inter = PropertiesUtils.getPropertyValue("autoAddExitGroupInterface");
    inter = inter.replace("{0}", form);
    inter = inter.replace("{1}", groupid);
    inter = inter.replace("{2}", members);
    inter = inter.replace("{3}", action);
    StringBuilder sb = new StringBuilder();
    sb.append("action=" + action);
    sb.append("command=autoAddExitGroup");
    sb.append("form=" + form);
    sb.append("groupid=" + groupid);
    sb.append("grouplist=" + members);
    sb.append("key_2015-08-27 17:53:45");
    String str = sb.toString();
    String md5 = ImMd5ForMember.md5(str);
    inter = inter.replace("{4}", md5);

    String res = SlRequest.getHtml(inter);

    AutoAddExitGroupModel resObj = (AutoAddExitGroupModel) SlJson.fromJson(res, AutoAddExitGroupModel.class);

    if (resObj.getData().equals("success")) {
      return "ok";
    } else {
      return "error";
    }
  }

  /**
   * removeMembers
   *
   * @param model
   *        model
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/removeMembers.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody String removeMembers(AddMembersIn model) throws Exception {
    String form = model.getForm();
    String action = model.getAction();
    String members = model.getMembers();
    String groupid = model.getGroupid();
    if (StringUtils.isEmpty(form) || StringUtils.isEmpty(action) || StringUtils.isEmpty(members) || StringUtils.isEmpty(groupid)) {
      return "error";
    }
    String imName = getImName(model.getGroupid());
    String inter = PropertiesUtils.getPropertyValue("autoAddExitGroupInterface");
    inter = inter.replace("{0}", imName);
    inter = inter.replace("{1}", groupid);
    inter = inter.replace("{2}", members);
    inter = inter.replace("{3}", action);
    StringBuilder sb = new StringBuilder();
    sb.append("action=" + action);
    sb.append("command=autoAddExitGroup");
    sb.append("form=" + imName);
    sb.append("groupid=" + groupid);
    sb.append("grouplist=" + members);
    sb.append("key_2015-08-27 17:53:45");
    String str = sb.toString();
    String md5 = MD5Util.Md5(str);
    inter = inter.replace("{4}", md5);

    String res = SlRequest.getHtml(inter);

    AutoAddExitGroupModel resObj = (AutoAddExitGroupModel) SlJson.fromJson(res, AutoAddExitGroupModel.class);

    if (resObj.getData().equals("success")) {
      return "ok";
    } else {
      return "error";
    }
  }

  /**
   * updateCardname
   *
   * @param model
   *        model
   * @return
   *         return
   */
  @RequestMapping("/updateCardname.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody String updateCardname(UpdateCardnameInVo model) {
    if (StringUtils.isEmpty(model.getGroupid()) || StringUtils.isEmpty(model.getUsername()) || StringUtils.isEmpty(model.getCardname())) {
      return null;
    }
    String inter = PropertiesUtils.getPropertyValue("modifyGroupCardNameInterface");
    inter = inter.replace("{0}", model.getGroupid()).replace("{1}", model.getUsername()).replace("{2}", model.getCardname());
    String res = SlRequest.getHtml(inter);
    ModifyGroupCardNameModel resJson = (ModifyGroupCardNameModel) SlJson.fromJson(res, ModifyGroupCardNameModel.class);
    if (resJson.getMsg().equals("成功")) {
      return model.getCardname();
    } else {
      return null;
    }
  }

  /**
   * forbiddenMember
   *
   * @param model
   *        model
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/forbiddenMember.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody String forbiddenMember(ForbiddenMemberInVo model) throws Exception {
    String inter = PropertiesUtils.getPropertyValue("forbiddenGroupUserInterface");
    if (StringUtils.isEmpty(model.getGroupid()) || StringUtils.isEmpty(model.getUsername()) || StringUtils.isEmpty(model.getEndtime())) {
      return null;
    }
    String imName = getImName(model.getGroupid());
    StringBuilder sb = new StringBuilder();
    sb.append("command=forbiddenGroupUser");
    sb.append("endtime=" + model.getEndtime());
    sb.append("groupid=" + model.getGroupid());
    sb.append("member=" + model.getUsername());
    sb.append("username=" + imName);
    sb.append("groupkey_2017-04-07 09:44:69");
    String sign = MD5Util.Md5(sb.toString());
    inter = inter.replace("{0}", imName).replace("{1}", model.getGroupid()).replace("{2}", model.getUsername()).replace("{3}", URLEncoder.encode(model.getEndtime(), "utf-8"))
        .replace("{4}", sign);
    String res = SlRequest.getHtml(inter);

    CommonResultJson resJson = (CommonResultJson) SlJson.fromJson(res, CommonResultJson.class);

    return resJson.getData();
  }

  /**
   * unforbiddenMember
   *
   * @param model
   *        model
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/unforbiddenMember.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody String unforbiddenMember(UnForbiddenMemberInVo model) throws Exception {
    String inter = PropertiesUtils.getPropertyValue("unforbiddenGroupUserInterface");
    if (StringUtils.isEmpty(model.getGroupid()) || StringUtils.isEmpty(model.getUsername())) {
      return null;
    }
    String imName = getImName(model.getGroupid());
    StringBuilder sb = new StringBuilder();
    sb.append("command=unforbiddenGroupUser");
    sb.append("groupid=" + model.getGroupid());
    sb.append("member=" + model.getUsername());
    sb.append("username=" + imName);
    sb.append("groupkey_2017-04-07 09:44:69");
    String sign = MD5Util.Md5(sb.toString());
    inter = inter.replace("{0}", imName).replace("{1}", model.getGroupid()).replace("{2}", model.getUsername())
        .replace("{3}", sign);
    String res = SlRequest.getHtml(inter);

    CommonResultJson resJson = (CommonResultJson) SlJson.fromJson(res, CommonResultJson.class);

    return resJson.getData();
  }

  /**
   * blackMember
   *
   * @param model
   *        model
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/blackMember.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody String blackMember(BlackMemberInVo model) throws Exception {
    String inter = PropertiesUtils.getPropertyValue("blackInGroupUserInterface");
    if (StringUtils.isEmpty(model.getGroupid()) || StringUtils.isEmpty(model.getUsername())) {
      return null;
    }
    String imName = getImName(model.getGroupid());
    StringBuilder sb = new StringBuilder();
    sb.append("command=blackInGroupUser");
    sb.append("groupid=" + model.getGroupid());
    sb.append("member=" + model.getUsername());
    sb.append("username=" + imName);
    sb.append("groupkey_2017-04-07 09:44:69");
    String sign = MD5Util.Md5(sb.toString());
    inter = inter.replace("{0}", imName).replace("{1}", model.getGroupid()).replace("{2}", model.getUsername()).replace("{3}", sign);
    String res = SlRequest.getHtml(inter);

    CommonResultJson resJson = (CommonResultJson) SlJson.fromJson(res, CommonResultJson.class);

    return resJson.getData();
  }

  /**
   * unblackMember
   *
   * @param model
   *        model
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  @RequestMapping("/unblackMember.do")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public @ResponseBody String unblackMember(UnBlackMemberInVo model) throws Exception {
    String inter = PropertiesUtils.getPropertyValue("blackOutGroupUserInterface");
    if (StringUtils.isEmpty(model.getGroupid()) || StringUtils.isEmpty(model.getUsername())) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("command=blackOutGroupUser");
    sb.append("groupid=" + model.getGroupid());
    sb.append("member=" + model.getUsername());
    sb.append("groupkey_2017-04-07 09:44:69");
    String sign = MD5Util.Md5(sb.toString());
    inter = inter.replace("{0}", model.getGroupid()).replace("{1}", model.getUsername()).replace("{2}", sign);
    String res = SlRequest.getHtml(inter);

    CommonResultJson resJson = (CommonResultJson) SlJson.fromJson(res, CommonResultJson.class);

    return resJson.getData();
  }

  /**
   * getPicByImname
   *
   * @param name
   *        name
   * @return
   *         return
   */
  private String getPicByImname(String name) {
    String inter = PropertiesUtils.getPropertyValue("interface_user_simple_info_2011Interface");
    if (StringUtils.isEmpty(name)) {
      return null;
    }
    int index = name.indexOf(":");
    if (index < 0) {
      return null;
    }
    String namenew = name.substring(index + 1, name.length());
    inter = inter.replace("{0}", namenew);
    String res = SlRequest.getHtml(inter);
    int beginIndex = res.indexOf("<avatar>");
    int endIndex = res.indexOf("</avatar>");
    if (beginIndex == -1 || endIndex == -1) {
      return null;
    }
    String url = res.substring(beginIndex + 8, endIndex);

    return url;
  }

  /**
   * getImName
   *
   * @param groupid
   *        groupid
   * @return
   *         return
   * @throws Exception
   *         Exception
   */
  private String getImName(String groupid) throws Exception {
    SysCommunityResource community = communityOperateService.getCommunityById(groupid);
    if (community == null || community.getCommunityHolder() == null) {
      throw new CustomeBusinessJsonExciption("查询群主失败");
    }
    SysUserResource sysUserResource = sysUserOperateService.getUserByEmail(community.getCommunityHolder());
    if (sysUserResource == null || sysUserResource.getImUserName() == null) {
      throw new CustomeBusinessJsonExciption("查询群主失败");
    }
    return sysUserResource.getImUserName();
  }

  /**
   * 获得伪数据
   *
   * @return
   *         return
   */
  /*private List<SysMember> getFakeData() {
    List<SysMember> members = new ArrayList<SysMember>();
    SysMember member1 = new SysMember() {

      *//**
       *
       */
  /*
  private static final long serialVersionUID = 907358474383331098L;

  {
  setUsertag("owner");
  setNickname("zhangsan");
  setCardname("张三");
  setUsername("zs");
  setSex("男");
  setAddtime("2017/03/24");
  setLastmessagetime("2017/04/24");
  setState("正常");
  }
  };
  SysMember member2 = new SysMember() {

  *//**
       *
       */
  /*
  private static final long serialVersionUID = 3099612659490179654L;

  {
  setUsertag("manager");
  setNickname("lisi");
  setCardname("李四");
  setUsername("ls");
  setSex("男");
  setAddtime("2017/03/24");
  setLastmessagetime("2017/04/24");
  setState("正常");
  }
  };
  SysMember member3 = new SysMember() {

  *//**
       *
       */
  /*
  private static final long serialVersionUID = -652529753335716565L;

  {
  setUsertag("manager");
  setNickname("wangwu");
  setCardname("王五");
  setUsername("ww");
  setSex("女");
  setAddtime("2017/03/24");
  setLastmessagetime("2017/04/24");
  setState("正常");
  }
  };
  SysMember member4 = new SysMember() {

  *//**
       *
       */
  /*
  private static final long serialVersionUID = -4631338200177989259L;

  {
  setUsertag("common");
  setNickname("zhaoliu");
  setCardname("赵六");
  setUsername("zl");
  setSex("女");
  setAddtime("2017/03/24");
  setLastmessagetime("2017/04/24");
  setState("禁言");
  }
  };
  SysMember member5 = new SysMember() {

  *//**
       *
       */
  /*
  private static final long serialVersionUID = -4750604293713114650L;

  {
  setUsertag("common");
  setNickname("tianqi");
  setCardname("田七");
  setUsername("tq");
  setSex("女");
  setAddtime("2017/03/24");
  setLastmessagetime("2017/04/24");
  setState("拉黑");
  }
  };
  members.add(member1);
  members.add(member2);
  members.add(member3);
  members.add(member4);
  members.add(member5);

  return members;
  }*/

}
