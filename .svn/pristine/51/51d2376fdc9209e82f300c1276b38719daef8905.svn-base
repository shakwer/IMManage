/**
 * File：FangOaAuthenAndAuthorRealm.java
 * Package：com.fang.im.management.realm
 * Author：jin
 * Date：2017年4月1日 下午3:18:08
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.fang.im.management.po.SysCity;
import com.fang.im.management.po.SysCommunityResource;
import com.fang.im.management.po.SysRole;
import com.fang.im.management.po.SysUserResource;
import com.fang.im.management.service.CommunityOperateService;
import com.fang.im.management.service.SysCityOperateService;
import com.fang.im.management.service.SysRoleOperateService;
import com.fang.im.management.service.SysUserOperateService;
import com.fang.im.management.utils.PropertiesUtils;

/**
 * <p>
 * Description: FangOaAuthenAndAuthorRealm
 * </p>
 *
 * @author jinshilei
 *         2017年4月1日
 * @version 1.0
 *
 */
public class FangOaAuthenAndAuthorRealm extends AuthorizingRealm {

  /**
   * 日志对象
   */
  private static final Logger LOGGER = LogManager.getLogger("error");

  /**
   * redis缓存管理器
   */
  @Autowired
  private RedisCacheManager redisCacheManager;

  /**
   * redisSessionDao(登录session管理)
   */
  @Autowired
  private RedisSessionDAO sessionDao;

  /**
   * 用户信息操作
   */
  @Autowired
  private SysUserOperateService sysUserOperateService;

  /**
   * 角色信息操作
   */
  @Autowired
  private SysRoleOperateService sysRoleOperateService;

  /**
   * 群信息操作
   */
  @Autowired
  private CommunityOperateService communityOperateService;

  /**
   * 城市信息操作
   */
  @Autowired
  private SysCityOperateService sysCityOperateService;

  @Override
  public void setName(String name) {
    super.setName("oaAuRealm");
  }

  /**
   * 认证
   *
   * @param token
   *        身份和凭证
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    String email = (String) token.getPrincipal();
    SysUserResource user = null;
    try {
      user = sysUserOperateService.getUserByEmail(email);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("doGetAuthenticationInfo---"+email+"-------"+sysUserOperateService+"----"+user);
    if (user != null) {
      try {
        // 更新用户的最后登录时间
        sysUserOperateService.updateUserInfo(user.getSysUserId(), null, null, new Date());
      } catch (Exception e) {
        e.printStackTrace();
      }
      SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, null,
          this.getName());
      return authenticationInfo;
    }
    throw new UnknownAccountException("未开通后台权限");
  }

  /**
   * 授权
   *
   * @param principals
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    SysUserResource userCurrent = (SysUserResource) principals.getPrimaryPrincipal();
    // 查询最新授权信息保存在该对象中
    SysUserResource user = null;
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    if (userCurrent != null) {
      try {
        user = sysUserOperateService.getUserByUserId(userCurrent.getSysUserId());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      SysRole role = user.getSysRole();
      if (role != null) {
        if (role.getRoleName().equals("超级管理员")) {
          authorizationInfo.addRole("超级管理员");
          // 获取超级管理员对用户的操作权限
          authorizationInfo.addStringPermission("user:delete,update,select:*");
          authorizationInfo.addStringPermission("user:addrole:*");
          authorizationInfo.addStringPermission("user:addcity:*");
          authorizationInfo.addStringPermission("user:updaterole:*");
          authorizationInfo.addStringPermission("user:deletecity:*");
          // 获取超级管理员对群的操作权限
          authorizationInfo.addStringPermission("community:add,delete,update,select:*");
        } else if ((role.getRoleName().equals("管理员"))) {
          authorizationInfo.addRole("管理员");
          SysRole communityRole = null;
          try {
            communityRole = sysRoleOperateService.getByRoleName("普通群主");
          } catch (Exception e) {
            e.printStackTrace();
          }
          Set<SysCity> cities = user.getSysCities();
          List<Integer> cityIds = new ArrayList<Integer>();
          Collection<SysCity> allcities = null;
          if (cities != null && cities.size() > 0) {
            int totalCity = 0;
            try {
              allcities = sysCityOperateService.getAllCities().values();
              totalCity = allcities.size();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (totalCity == cities.size()) {
              // 具有所有城市权限，则不过滤城市，查询所有城市的用户
              cityIds = null;
            } else {
              for (SysCity city : cities) {
                cityIds.add(city.getSysCityId());
              }
            }
          }
          // 获取管理员对用户的操作权限
          // 对于添加用户，管理员角色只能添加普通群主角色用户
          authorizationInfo.addStringPermission("user:addrole:" + communityRole.getSysRoleId());
          // 对于修改角色，只能修改自己的角色和普通群主的角色
          authorizationInfo.addStringPermission("user:updaterole:" + communityRole.getSysRoleId());
          // 对于添加用户，管理员角色只能添加自己权限内的城市
          if (cityIds == null) {
            for (SysCity city : allcities) {
              authorizationInfo.addStringPermission("user:addcity:" + city.getSysCityId());
              authorizationInfo.addStringPermission("user:deletecity:" + city.getSysCityId());
            }
          } else {
            for (Integer cityId : cityIds) {
              authorizationInfo.addStringPermission("user:addcity:" + cityId);
              authorizationInfo.addStringPermission("user:deletecity:" + cityId);
            }
          }
          List<SysUserResource> users = null;
          if (communityRole != null) {
            try {
              users = sysUserOperateService.getAllUserByRoleAndCities(communityRole.getSysRoleId(),
                  cityIds);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          if (users != null && users.size() > 0) {
            for (SysUserResource item : users) {
              authorizationInfo.addStringPermission("user:delete,update,select:"
                  + item.getSysUserId());
            }
          }
          // 管理员角色，只能查看、更改自己的信息，不能删除自己
          authorizationInfo.addStringPermission("user:update,select:" + user.getSysUserId());
          // 获取管理员对群的操作权限
          List<SysCommunityResource> communities = null;
          if (cityIds == null) {
            authorizationInfo.addStringPermission("community:add,delete,update,select:*");
          } else {
            try {
              communities = communityOperateService.getCommunityByCities(cityIds);
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (communities != null && communities.size() > 0) {
              for (SysCommunityResource community : communities) {
                authorizationInfo.addStringPermission("community:add,delete,update,select:"
                    + community.getCommunityId());
              }
            }
          }
        } else if ((role.getRoleName().equals("普通群主"))) {
          authorizationInfo.addRole("普通群主");
          // 普通群主对系统用户没有操作权限
          // 获取普通群主对群的操作权限
          List<SysCommunityResource> communities = null;
          try {
            communities = communityOperateService.getCommunityByUser(user.getEmail());
          } catch (Exception e) {
            e.printStackTrace();
          }
          if (communities != null && communities.size() > 0) {
            for (SysCommunityResource community : communities) {
              authorizationInfo.addStringPermission("community:add,delete,update,select:"
                  + community.getCommunityId());
            }
          }
        }
      }
    }

    return authorizationInfo;
  }

  /**
   * 重写cache的key的生成规则，原规则为返回SimplePrincipalCollection对象，改为返回登录邮箱字符串
   */
  @Override
  protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
    if (principals != null) {
      SysUserResource sysUser = (SysUserResource) principals.getPrimaryPrincipal();
      if (sysUser != null) {
        return sysUser.getEmail() + "_" + PropertiesUtils.getRunState();
      }
    }
    return null;
  }

  /**
   *
   * 通过邮箱踢该用户下线。
   *
   * @param email
   *        登录邮箱
   */
  public void kickoutUserByEmail(String email) {
    Session session = getSessionByEmail(email);
    if (session != null) {
      sessionDao.delete(session);
      System.out.println("将用户踢下线：" + email);
    }
  }

  /**
   *
   * updateCurrentUserSession
   *
   * @param email
   *        邮箱
   */
  public void updateCurrentUserSession(String email) {
    Session session = getSessionByEmail(email);
    if (session != null) {
      SysUserResource user = null;
      try {
        user = sysUserOperateService.getUserByEmail(email);
      } catch (Exception e) {
        e.printStackTrace();
      }
      SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection(user,
          this.getName());
      session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, simplePrincipalCollection);
      sessionDao.update(session);
      System.out.println("更新了session中的用户信息");
    }
  }

  /**
   *
   * getSessionByEmail
   *
   * @param email
   *        邮箱
   * @return 返回值
   */
  private Session getSessionByEmail(String email) {
    Collection<Session> activeSessions = sessionDao.getActiveSessions();
    if (activeSessions != null && activeSessions.size() > 0) {
      for (Session session : activeSessions) {
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
            .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (principalCollection != null) {
          SysUserResource user = (SysUserResource) principalCollection.getPrimaryPrincipal();
          if (user != null && email.equals(user.getEmail())) {
            return session;
          }
        }
      }
    }
    return null;
  }

  /**
   *
   * 清除当前用户的授权缓存
   */
  public void clearCurrentUserAuthorazitionCache() {
    super.doClearCache(SecurityUtils.getSubject().getPrincipals());
  }

  /**
   *
   * 通过邮箱清除某用户的授权缓存
   *
   * @param email
   *        登录用户的邮箱(不带@fang后缀)
   * @return 返回值
   */
  public boolean clearAuthorazitionCacheByEmail(String email) {
    // 获取所有授权缓存
    Cache<Object, AuthorizationInfo> authorizationCache = getAuthorizationCache();
    if (authorizationCache != null && authorizationCache.size() > 0) {
      if (authorizationCache.keys().contains(email)) {
        authorizationCache.remove(email);
        return true;
      }
    }
    return false;
  }

  /**
   *
   * 清除所有用户的授权缓存
   *
   * @return 返回值
   */
  public boolean clearAllUserAuthorazitionCache() {
    // 获取所有授权缓存
    Cache<Object, AuthorizationInfo> authorizationCache = getAuthorizationCache();
    if (authorizationCache != null && authorizationCache.size() > 0) {
      // 循环删除所有授权缓存
      for (Object key : authorizationCache.keys()) {
        String keyString = new String((byte[]) key);
        authorizationCache.remove(keyString.replaceAll(redisCacheManager.getKeyPrefix(), ""));
      }
      return true;
      // 一次性删除集合所有元素,同时会将session删除，不使用该方法
      // authorizationCache.clear();
      // return true;
    }
    return false;
  }

}
