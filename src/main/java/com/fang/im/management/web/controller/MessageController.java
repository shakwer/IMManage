package com.fang.im.management.web.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fang.common.utils.StringUtils;
import com.fang.common.network.HttpClientUtils;
import com.fang.im.management.po.SysCity;
import com.fang.im.management.po.SysCommunityResource;
import com.fang.im.management.po.SysRole;
import com.fang.im.management.po.SysUserResource;
import com.fang.im.management.service.CommunityOperateService;
import com.fang.im.management.service.SysCityOperateService;
import com.fang.im.management.service.SysUserOperateService;
import com.fang.im.management.systemexception.CustomeBusinessJsonExciption;
import com.fang.im.management.utils.ImMd5;
import com.fang.im.management.utils.PropertiesUtils;
import com.fang.im.management.web.vo.GetMessagesIn;
import com.fang.im.management.web.vo.out.CommunityCustomOut;
import com.fang.im.management.web.vo.out.GetGroupInfoOut;
import com.fang.im.management.web.vo.out.GetGroupMessageListOut;
import com.fang.im.management.web.vo.out.GetMessagesOut;
import com.fang.im.management.web.vo.out.ImUserServiceInterfaceUrlOut;
import com.fang.im.management.web.vo.out.Message;

/**
 * 消息管理控制器
 *
 * @author zhaozele
 */
@Controller
public class MessageController {

  /**
   * 群service
   */
  @Autowired
  private CommunityOperateService communityOperateService;

  /**
   * 城市service
   */
  @Autowired
  private SysCityOperateService sysCityOperateService;

  /**
   * 用户
   */
  @Autowired
  private SysUserOperateService sysUserOperateService;

  /**
   * imMd5加密
   */
  private static ImMd5 imMd5 = new ImMd5();

  /**
   * 获取消息信息
   *
   * @return 视图
   * @throws Exception
   *         异常
   */
  @RequestMapping("/message")
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public String getMessages(Model model, String communityId) throws Exception {
	  model.addAttribute("communityId",communityId);
    return "views/message";
  }

  /**
   * 切换群功能方法
   *
   * @param q
   *        搜索条件
   * @param pageIndex
   *        当前页
   * @param pageSize
   *        每页大小
   * @return 群集合
   * @throws Exception
   *         异常
   */
  @RequestMapping("/searchcommunitybyid")
  @ResponseBody
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public String searchCommunity(String q, Integer pageIndex, Integer pageSize) throws Exception {

    // 自定义参数
    String communityId = ""; // 群id
    String communityName = ""; // 群名称
    Integer pageindex = 1;
    Integer pagesize = 6;
    List<SysCommunityResource> communities = new ArrayList<SysCommunityResource>();
    String flag = ""; // 标识位，标识q的内容类型

    // 验证参数
    if (pageIndex != null && pageIndex > 0) {
      pageindex = pageIndex;
    }
    if (pageSize != null && pageSize > 0) {
      pagesize = pageSize;
    }
    if (!StringUtils.isEmpty(q)) {
      if (StringUtils.isNumeric(q)) {
        flag = "id";
        communityId = q;
        // 验证权限
        if (!validPermission(communityId)) {
          throw new CustomeBusinessJsonExciption("无权限");
        }
        SysCommunityResource community = communityOperateService.getCommunityById(communityId);
        if (community != null) {
          communities.add(community);
        }
      } else {
        flag = "name";
        communityName = q;
        List<SysCommunityResource> list = communityOperateService.getByCommunityName(communityName,
            pageindex, pagesize, "desc");
        // 验证权限
        if (list != null && list.size() > 0) {
          for (SysCommunityResource community : list) {
            if (validPermission(community.getCommunityId())) {
              communities.add(community);
            }
          }
        }
      }
    } else {
      communities = getCommunitiesByRole(2, pageindex, pagesize, "desc");
    }
    // 返回
    String result = "total:{0},totalPage:{1},pageIndex:{2},data:{3}"; // 返回结果字符串
    String data = "[]";
    int totalPage = 0; // 总页数
    long total = 0; // 总数
    if (communities != null && communities.size() > 0) {
      if (flag.equals("id")) {
        total = 1;
      } else if (flag.equals("name")) {
        total = communityOperateService.getCommunityCountByName(communityName);
      } else {
        total = getCommunityCountByRole(); // 获取该登录用户管理的群的总数
      }
      totalPage = (int) Math.ceil(Double.parseDouble(String.valueOf(total)) / pagesize);
      // 获取群头像
      GetGroupInfoOut getGroupInfoOut = getGroupInfo(communities);
      if (getGroupInfoOut != null) {
        List<ImUserServiceInterfaceUrlOut> urlOuts = getGroupInfoOut.getData(); // 群信息集合
        List<CommunityCustomOut> communityCustomOuts = new ArrayList<CommunityCustomOut>();
        if (urlOuts != null && urlOuts.size() > 0) {
          for (SysCommunityResource communityResource : communities) {
            CommunityCustomOut out = new CommunityCustomOut();
            out.setCommunityId(communityResource.getCommunityId());
            out.setCommunityName(communityResource.getCommunityName());
            for (ImUserServiceInterfaceUrlOut urlOut : urlOuts) {
              if (communityResource.getCommunityId().equals(urlOut.getGroupid())) {
                if (!urlOut.getPic().equals("null") && !StringUtils.isEmpty(urlOut.getPic())) {
                  out.setPic(urlOut.getPic());
                } else {
                  out.setPic("");
                }
              }
            }
            communityCustomOuts.add(out);
          }
          data = JSON.toJSONString(communityCustomOuts);
        }
      }
      result = MessageFormat.format(result, total, totalPage, pageindex, data);
    } else {
      result = MessageFormat.format(result, total, totalPage, pageindex, data);
    }
    result = "{" + result + "}";
    return result;
  }

  /**
   * 获取消息分页数据
   *
   * @param getMessageIn
   *        输入参数
   * @return 消息json串
   * @throws Exception
   *         异常
   */
  @RequestMapping("/getPageListMessages.do")
  @ResponseBody
  @RequiresRoles(value = {"超级管理员", "管理员", "普通群主"}, logical = Logical.OR)
  public String getPagelistMessages(GetMessagesIn getMessageIn) throws Exception {
    // 自定义参数
    Integer pageSize = 20;
    Integer pageIndex = 1;
    String communityId = "";
    String keyWords = "";
    String spokeMan = "";
    String startTime = "";
    String endTime = "";
    List<SysCommunityResource> communityids = new ArrayList<SysCommunityResource>();
    GetGroupInfoOut groupInfoOut = null;
    ImUserServiceInterfaceUrlOut imUserServiceInterfaceUrlOut = null; // 群信息类

    // 返回类
    GetGroupMessageListOut listOut = new GetGroupMessageListOut();

    // 对输入参数进行处理
    // 时间处理
    if (!StringUtils.isEmpty(getMessageIn.getLastSpeakingTime())) {
      Map<String, String> lastSpeakingTime = dealLastSpeakingTime(getMessageIn
          .getLastSpeakingTime());
      if (lastSpeakingTime != null) {
        if (lastSpeakingTime.containsKey("starttime")) {
          startTime = lastSpeakingTime.get("starttime");
        }
        if (lastSpeakingTime.containsKey("endtime")) {
          endTime = lastSpeakingTime.get("endtime");
        }
      }
    }
    // 群id处理
    if (!StringUtils.isEmpty(getMessageIn.getCommunityId())) {
      communityId = getMessageIn.getCommunityId();
      // 验证是否有权限
      if (!validPermission(communityId)) {
        throw new CustomeBusinessJsonExciption("无权限");
      }
      // 调用群信息接口
      SysCommunityResource community = communityOperateService.getCommunityById(communityId);
      communityids.add(community);
      groupInfoOut = getGroupInfo(communityids);
    } else {
      // 获取最近的群的id
      communityids = getCommunitiesByRole(1, 1, 1, "desc");
      // 调用群信息接口
      groupInfoOut = getGroupInfo(communityids);
      if (communityids != null && communityids.size() > 0) {
        communityId = communityids.get(0).getCommunityId();
      }
    }
    if (!StringUtils.isEmpty(getMessageIn.getKeyWords())) {
      keyWords = getMessageIn.getKeyWords();
    }
    if (!StringUtils.isEmpty(getMessageIn.getSpokesMan())) {
      spokeMan = getMessageIn.getSpokesMan();
    }
    if (getMessageIn.getPageIndex() != null && getMessageIn.getPageIndex() > 0) {
      pageIndex = getMessageIn.getPageIndex();
    }
    if (getMessageIn.getPageSize() != null && getMessageIn.getPageSize() > 0) {
      pageSize = getMessageIn.getPageSize();
    }

    // 群信息
    if (groupInfoOut != null) {
      List<ImUserServiceInterfaceUrlOut> urlOuts = groupInfoOut.getData();
      if (urlOuts != null && urlOuts.size() > 0) {
        imUserServiceInterfaceUrlOut = urlOuts.get(0);
        listOut.setGroupid(imUserServiceInterfaceUrlOut.getGroupid());
        listOut.setGroupName(imUserServiceInterfaceUrlOut.getGroupname());
        listOut.setCount(imUserServiceInterfaceUrlOut.getCount());
        listOut.setLimit(imUserServiceInterfaceUrlOut.getLimit());
      }
    }

    // 群二维码
    String groupQr = getGroupQrUrl(communityId,
        communityOperateService.getCommunityById(communityId).getCommunityHolder());
    listOut.setGroupQr(groupQr);

    // 调用群消息搜索接口
    GetMessagesOut getMessageOut = getMessages(communityId, keyWords, spokeMan, startTime, endTime,
        pageIndex, pageSize);
    List<Message> messages = new ArrayList<Message>();
    if (getMessageOut != null) {
      messages = getMessageOut.getData();
      if (messages != null && messages.size() > 0) {
        // 对消息进行处理
        messages = subMsgByMessageType(messages);
        int totalMsg = Integer.valueOf(getMessageOut.getMsg()); // 总条数
        int totalPage = 0; // 总页数
        if (totalMsg > 0) {
          totalPage = (int) Math.ceil(Double.parseDouble(String.valueOf(totalMsg)) / pageSize);
          listOut.setTotalMsg(totalMsg);
          listOut.setPageIndex(pageIndex);
          listOut.setTotalPage(totalPage);
          listOut.setData(messages);
          return JSON.toJSONString(listOut);
        }
      }
    }
    // 接口返回为空时
    listOut.setTotalMsg(0);
    listOut.setTotalPage(0);
    listOut.setPageIndex(pageIndex);
    listOut.setData(messages);
    return JSON.toJSONString(listOut);
  }

  /**
   * 获取当前登录用户管理的群的总数
   *
   * @return 当前登录用户管理的群的总数
   * @throws Exception
   *         异常
   */
  private Long getCommunityCountByRole() throws Exception {

    Long count = (long) 0;
    // 获取当前登录用户
    Subject subject = SecurityUtils.getSubject();
    SysUserResource user = (SysUserResource) subject.getPrincipal();
    if (user != null) {
      SysRole role = user.getSysRole();
      if (role != null) {
        if (role.getRoleName().equals("超级管理员")) {
          count = communityOperateService.getCommunityCount();
        } else if (role.getRoleName().equals("管理员")) {
          // 根据城市id集合查询所有的群
          Set<SysCity> cities = user.getSysCities();
          List<Integer> cityIds = new ArrayList<Integer>();
          if (cities != null && cities.size() > 0) {
            if (sysCityOperateService.getAllCities().size() == cities.size()) {
              cityIds = null;
            } else {
              for (SysCity city : cities) {
                cityIds.add(city.getSysCityId());
              }
            }
          }
          if (cityIds != null && cityIds.size() > 0) {
            count = communityOperateService.getCommunityCountByCities(cityIds);
          } else {
            // 具有全国权限的管理员
            count = communityOperateService.getCommunityCount();
          }
        } else if (role.getRoleName().equals("普通群主")) {
          count = communityOperateService.getCommunityCountByUser(user.getEmail());
        } else {
          throw new CustomeBusinessJsonExciption("无权限");
        }
      } else {
        throw new CustomeBusinessJsonExciption("用户不存在");
      }
    }
    return count;
  }

  /**
   * 根据角色获取管理的群（按创建时间倒序）
   *
   * @param type
   *        方法类型（1为取得最新群id，2为切换群功能，获取群id列表）
   * @param pageIndex
   *        页码
   * @param pageSize
   *        每页大小
   * @param order
   *        排序(desc为倒序,asc为正序)
   * @return 群id集合
   * @throws Exception
   *         异常
   */
  private List<SysCommunityResource> getCommunitiesByRole(Integer type, Integer pageIndex,
                                                          Integer pageSize, String order)
      throws Exception {
    // 返回结果集
    List<SysCommunityResource> communities = new ArrayList<SysCommunityResource>();
    // 获取当前登录用户
    Subject subject = SecurityUtils.getSubject();
    SysUserResource user = (SysUserResource) subject.getPrincipal();
    if (user != null) {
      SysRole userRole = user.getSysRole();
      if (userRole != null) {
        if (userRole.getRoleName().equals("超级管理员")) {
          // 根据创建时间倒序查出群集合
          List<SysCommunityResource> sysCommunities = communityOperateService
              .getPageListByCreateTime(pageIndex, pageSize, order);
          if (sysCommunities != null && sysCommunities.size() > 0) {
            if (type == 1) {
              communities.add(sysCommunities.get(0));
            } else if (type == 2) {
              for (SysCommunityResource community : sysCommunities) {
                communities.add(community);
              }
            }
          }
        } else if (userRole.getRoleName().equals("管理员")) {
          // 根据城市id集合查询所有的群
          Set<SysCity> cities = user.getSysCities();
          List<Integer> cityIds = new ArrayList<Integer>();
          if (cities != null && cities.size() > 0) {
            if (sysCityOperateService.getAllCities().size() == cities.size()) {
              cityIds = null;
            } else {
              for (SysCity city : cities) {
                cityIds.add(city.getSysCityId());
              }
            }
          }
          if (cityIds != null && cityIds.size() > 0) {
            List<SysCommunityResource> sysCommunities = communityOperateService
                .getPageListByCities(cityIds, pageIndex, pageSize, order);
            if (sysCommunities != null && sysCommunities.size() > 0) {
              if (type == 1) {
                // 取得最新群id
                communities.add(sysCommunities.get(0));
              } else if (type == 2) {
                // 切换群功能，获取群id列表
                for (SysCommunityResource community : sysCommunities) {
                  communities.add(community);
                }
              }
            }
          } else {
            // 具有全国权限的管理员
            List<SysCommunityResource> sysCommunities = communityOperateService
                .getPageListByCreateTime(pageIndex, pageSize, order);
            if (sysCommunities != null && sysCommunities.size() > 0) {
              if (type == 1) {
                communities.add(sysCommunities.get(0));
              } else if (type == 2) {
                for (SysCommunityResource community : sysCommunities) {
                  communities.add(community);
                }
              }
            }
          }
        } else if (userRole.getRoleName().equals("普通群主")) {
          // 根据群主名称查出所有的群
          List<SysCommunityResource> sysCommunities = communityOperateService.getPageListByUser(
              user.getEmail(), pageIndex, pageSize, order);
          if (sysCommunities != null && sysCommunities.size() > 0) {
            if (type == 1) {
              // 取得最新群id
              communities.add(sysCommunities.get(0));
            } else if (type == 2) {
              // 切换群功能，获取群id列表
              for (SysCommunityResource community : sysCommunities) {
                communities.add(community);
              }
            }
          }
        }
      } else {
        throw new CustomeBusinessJsonExciption("无角色");
      }
    } else {
      throw new CustomeBusinessJsonExciption("用户不存在");
    }

    return communities;
  }

  /**
   * 处理最后发言时间
   *
   * @param lastSpeakingTime
   *        最后发言时间
   * @return 最后发言时间
   * @throws Exception
   *         异常
   */
  private Map<String, String> dealLastSpeakingTime(String lastSpeakingTime) throws Exception {

    Map<String, String> map = new HashMap<String, String>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Date begin = null;
    Date end = null;
    if (!StringUtils.isEmpty(lastSpeakingTime)) {
      if (lastSpeakingTime.trim().startsWith("-")) {
        lastSpeakingTime = lastSpeakingTime.substring(1, lastSpeakingTime.length());
        Date enddate = simpleDateFormat.parse(lastSpeakingTime);
        Calendar endC = Calendar.getInstance();
        endC.setTime(enddate);
        endC.add(Calendar.DAY_OF_MONTH, 1);
        lastSpeakingTime = simpleDateFormat.format(endC.getTime());
        map.put("endtime", lastSpeakingTime);
        return map;
      }
      if (lastSpeakingTime.trim().endsWith("-")) {
        lastSpeakingTime = lastSpeakingTime.substring(0, lastSpeakingTime.length() - 1);
      }
      Date now = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(now);
      if (lastSpeakingTime.equals("1个月以内")) {
        calendar.add(Calendar.MONTH, -1);
        begin = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        end = calendar.getTime();
      } else if (lastSpeakingTime.equals("1-3个月以内")) {
        calendar.add(Calendar.MONTH, -1);
        end = calendar.getTime();
        calendar.add(Calendar.MONTH, -2);
        begin = calendar.getTime();
      } else if (lastSpeakingTime.equals("3-6个月以内")) {
        calendar.add(Calendar.MONTH, -3);
        end = calendar.getTime();
        calendar.add(Calendar.MONTH, -6);
        begin = calendar.getTime();
      } else if (lastSpeakingTime.equals("6-12个月以内")) {
        calendar.add(Calendar.MONTH, -6);
        end = calendar.getTime();
        calendar.add(Calendar.MONTH, -12);
        begin = calendar.getTime();
      } else if (lastSpeakingTime.equals("12个月以上")) {
        calendar.add(Calendar.MONTH, -12);
        end = calendar.getTime();
      }
      if (begin != null) {
        map.put("starttime", simpleDateFormat.format(begin));
      }
      if (end != null) {
        map.put("endtime", simpleDateFormat.format(end));
      }
      if (begin == null && end == null) {
        if (lastSpeakingTime.length() > 10) {
          Date enddate = simpleDateFormat.parse(lastSpeakingTime.substring(11, 21));
          Calendar endC = Calendar.getInstance();
          endC.setTime(enddate);
          endC.add(Calendar.DAY_OF_MONTH, 1);

          map.put("starttime", lastSpeakingTime.substring(0, 10));
          map.put("endtime", simpleDateFormat.format(endC.getTime()));
        } else {
          map.put("starttime", lastSpeakingTime);
        }
      }
      if (map != null) {
        if (map.size() > 1) {
          int result = simpleDateFormat.parse(map.get("starttime")).compareTo(
              simpleDateFormat.parse(map.get("endtime")));
          if (result > 0) {
            String temp = map.get("starttime");
            map.put("starttime", map.get("endtime"));
            map.put("endtime", temp);
          }
          return map;
        } else if (map.size() > 0) {
          return map;
        }
      }
    }
    return null;
  }

  /**
   * 验证用户是否具有某个群查看权限
   *
   * @param communityId
   *        群id
   * @return true代表有查看权限，false代表没有查看权限
   * @throws Exception
   *         异常
   */
  private boolean validPermission(String communityId) throws Exception {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isPermitted("Community:select:" + communityId)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 根据群id获取群信息
   *
   * @param communities
   *        群id集合
   * @return 群信息
   * @throws Exception
   *         异常
   */
  private GetGroupInfoOut getGroupInfo(List<SysCommunityResource> communities) throws Exception {
    if (communities == null || communities.size() <= 0) {
      return null;
    }
    // 拼接ids
    String ids = "";
    for (SysCommunityResource sysCommunityResource : communities) {
      ids = ids + sysCommunityResource.getCommunityId() + ",";
    }
    ids = ids.substring(0, ids.length() - 1);
    // 调用根据群id获取群信息的接口
    String urlGroupInfo = MessageFormat.format(PropertiesUtils.getPropertyValue("im_getGroupInfo"),
        ids);
    // 获取json结果
    String resultCommunity = HttpClientUtils.requestUrl(urlGroupInfo);
    // 解析json结果并填充页面返回值
    if (!StringUtils.isEmpty(resultCommunity)) {
      try {
        return JSON.parseObject(resultCommunity, GetGroupInfoOut.class);
      } catch (Exception ex) {
        return null;
      }
    }
    return null;
  }

  /**
   * 根据群id和其他筛选条件获取群消息（分页）
   *
   * @param communityId
   *        群id
   * @param keyWords
   *        关键字
   * @param spokeMan
   *        发言人
   * @param startTime
   *        最后发言开始时间
   * @param endTime
   *        最后发言结束时间
   * @param pageIndex
   *        页码
   * @param pageSize
   *        每页大小
   * @return 群消息
   * @throws Exception
   *         异常
   */
  private GetMessagesOut getMessages(String communityId, String keyWords, String spokeMan,
                                     String startTime, String endTime, Integer pageIndex,
                                     Integer pageSize) throws Exception {

    if (StringUtils.isEmpty(communityId)) {
      return null;
    }
    // 调用群消息筛选接口
    // 参数的顺序是groupid={0}&form={1}&messagestarttime={2}&messageendtime={3}&order={4}&pageindex={5}&pagesize={6}&messagekey={7}
    String urlMessage = MessageFormat.format(
        PropertiesUtils.getPropertyValue("im_userServiceInterface"), communityId, spokeMan,
        startTime, endTime, "desc", pageIndex, pageSize, keyWords);
    // 获取json结果
    String resultMessage = HttpClientUtils.requestUrl(urlMessage);

    // 解析json结果
    if (!StringUtils.isEmpty(resultMessage)) {
      try {
        return JSON.parseObject(resultMessage, GetMessagesOut.class);
      } catch (Exception e) {
        // 接口返回错误
        return null;
      }
    }
    return null;
  }

  /**
   * 获取群二维码链接
   *
   * @param communityId
   *        群id
   * @param communityHolder
   *        群主
   * @return 群二维码链接
   * @throws Exception
   *         异常
   */
  private String getGroupQrUrl(String communityId, String communityHolder) throws Exception {
    String result = "";
    String imUser = sysUserOperateService.getUserByEmail(communityHolder).getImUserName();
    String secretKey = PropertiesUtils.getPropertyValue("groupQrSecretKey");
    String publicKey = imMd5.getMD5(imUser + PropertiesUtils.getPropertyValue("groupQrPublicKey"));
    String keyParams = MessageFormat.format("command=getgroupqrgroupid={0}original={1}",
        communityId, false);
    String sign = imMd5.getMD5(keyParams + publicKey + secretKey);

    result = MessageFormat.format(PropertiesUtils.getPropertyValue("im_getGroupQr"), communityId,
        imUser, sign, false);
    return result;
  }

  /**
   * 根据消息类型对消息进行处理
   *
   * @param messages
   *        消息集合
   * @return 消息集合
   * @throws Exception
   *         异常
   */
  private List<Message> subMsgByMessageType(List<Message> messages) throws Exception {
    for (Message message : messages) {
      if (message.getCommand().equals("group_video")) {
        message.setMessage(message.getMessage().split(";")[0]);
      } else if (message.getCommand().equals("group_voice")) {
        message.setMessage(message.getMessage().split(";")[0]);
      }
    }
    return messages;
  }
}
