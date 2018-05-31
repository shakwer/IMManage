/**
 * File：OACredentialsMatcher.java
 * Package：com.fang.im.management.shiro
 * Author：jin
 * Date：2017年4月5日 上午11:11:24
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * <p>
 * Description: OACredentialsMatcher
 * </p>
 *
 * @author jinshilei
 *         2017年4月5日
 * @version 1.0
 *
 */
public class OACredentialsMatcher implements CredentialsMatcher {

  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    // char[] tokenChars = (char[]) token.getCredentials();
    // char[] infoChars = (char[]) info.getCredentials();
    return true;
  }
}
