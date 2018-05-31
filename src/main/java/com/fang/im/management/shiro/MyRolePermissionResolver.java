/**
 * File：MyRolePermissionResolver.java
 * Package：com.fang.im.management.shiro
 * Author：jin
 * Date：2017年4月13日 上午10:18:29
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.shiro;

import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;

/**
 * <p>
 * Description: MyRolePermissionResolver
 * </p>
 *
 * @author jinshilei
 *         2017年4月13日
 * @version 1.0
 *
 */
public class MyRolePermissionResolver implements RolePermissionResolver {

  @Override
  public Collection<Permission> resolvePermissionsInRole(String role) {
    // List<Permission> permissions = new ArrayList<Permission>();
    // WildcardPermission userPermission = new
    // WildcardPermission("user:add,delete,update,select:*");
    // permissions.add(userPermission);
    // return permissions;
    return null;
  }
}
