package com.fang.im.management.utils;

import java.util.regex.Pattern;

/**
 * imginterface
 *
 * @author YZT_SouFun
 */
public class AuthToken {

  /**
   * FIELD_SEPERATOR
   */
  static final String FIELD_SEPERATOR = "^";

  /**
   * SEPERATOR_PATTERN
   */
  static final Pattern SEPERATOR_PATTERN = Pattern.compile("[\\^]");

  /**
   * version
   */
  public String version;

  /**
   * uid
   */
  public String uid;

  /**
   * uip
   */
  public String uip;

  /**
   * timestamp
   */
  public long timestamp;

  /**
   * isValid
   *
   * @param uid
   *        uid
   * @param uip
   *        uip
   * @return boolean
   */
  public boolean isValid(String uid, String uip) {
    // ��ʱ���޶�IP
    if (this.uid.equals(uid)) { // && this.uip.equals(uip)) {
      long timeDiff = Math.abs((System.currentTimeMillis() / 1000) - this.timestamp);
      if (timeDiff < 3600) {
        return true;
      }
    }

    return false;
  }

  /**
   * authparse
   *
   * @param data
   *        data
   * @return AuthToken
   * @throws ParserException
   *         异常
   */
  public static AuthToken parse(String data) throws ParserException {
    System.err.println(data);
    String[] fields = SEPERATOR_PATTERN.split(data);
    if (fields.length > 2) {
      // String mutex = fields[0];
      String version = fields[1];
      if ("1.0".equals(version)) {
        AuthToken authToken = new AuthToken();
        authToken.version = version;
        authToken.uid = fields[2];
        authToken.uip = fields[3];
        authToken.timestamp = Long.parseLong(fields[4]);
        return authToken;
      }
    }
    throw new ParserException("failed to parse AuthToken from '" + data + "'");
  }
}
