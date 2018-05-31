package com.fang.im.management.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * AuthTokenFactory
 *
 * @author YZT_SouFun
 */
public class AuthTokenFactory {

  /**
   * secretKeyFactory
   */
  static SecretKeyFactory secretKeyFactory = null;

  /**
   * CIPHER
   */
  static final String CIPHER = "DES/CBC/PKCS5Padding";
  /**
   * getInstance
   */
  static {
    try {
      secretKeyFactory = SecretKeyFactory.getInstance("DES");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  /**
   * UTF8
   */
  private static final String UTF8 = "UTF-8";

  /**
   * AUTH_TOKEN_VERSION
   */
  private static final String AUTH_TOKEN_VERSION = "1.0";

  /**
   * hex->str & str->hex
   *
   * @param ss
   *        ss
   * @return byte[]
   */
  public static byte[] stringToHex(String ss) {
    byte[] digest = new byte[ss.length() / 2];
    for (int i = 0; i < digest.length; i++) {
      String byteString = ss.substring(2 * i, 2 * i + 2);
      int byteValue = Integer.parseInt(byteString, 16);
      digest[i] = (byte) byteValue;
    }
    return digest;
  }

  /**
   * hexToString
   *
   * @param b
   *        b
   * @return String
   */
  public static String hexToString(byte[] b) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      String plainText = Integer.toHexString(0xff & b[i]);
      if (plainText.length() < 2) {
        hexString.append("0");
      }
      hexString.append(plainText);
    }

    return hexString.toString();
  }

  /**
   * convertKeyIv
   *
   * @param text
   *        text
   * @return byte[]
   * @throws IOException
   *         异常
   */
  private static byte[] convertKeyIv(String text) throws IOException {
    if (text.length() == 8) {
      return text.getBytes(UTF8);
    }
    if (text.startsWith("0x") && text.length() == 32) {
      byte[] result = new byte[8];
      for (int i = 0; i < text.length(); i += 2) {
        if (text.charAt(i++) == '0' && text.charAt(i++) == 'x') {
          try {
            result[i / 4] = (byte) Integer.parseInt(text.substring(i, i + 2), 16);
          } catch (Exception e) {
            throw new IOException("TXT '" + text + "' is invalid!");
          }
        }
      }
      return result;
    }
    throw new IOException("TXT '" + text + "' is invalid!");
  }

  /**
   * encrypt
   *
   * @param data
   *        data
   * @param secretKey
   *        secretKey
   * @param iv
   *        iv
   * @return byte[]
   * @throws Exception
   *         异常
   */
  public static byte[] encrypt(byte[] data, SecretKey secretKey, IvParameterSpec iv)
      throws Exception {
    Cipher cipher = Cipher.getInstance(CIPHER);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

    return cipher.doFinal(data);
  }

  /**
   * decrypt
   *
   * @param message
   *        message
   * @param secretKey
   *        secretKey
   * @param iv
   *        iv
   * @return String
   * @throws Exception
   *         异常
   */
  public static String decrypt(String message, SecretKey secretKey, IvParameterSpec iv)
      throws Exception {
    byte[] data = stringToHex(message);
    return decrypt(data, secretKey, iv);
  }

  /**
   * decrypt
   *
   * @param data
   *        data
   * @param secretKey
   *        secretKey
   * @param iv
   *        iv
   * @return String
   * @throws Exception
   *         异常
   */
  public static String decrypt(byte[] data, SecretKey secretKey, IvParameterSpec iv)
      throws Exception {
    Cipher cipher = Cipher.getInstance(CIPHER);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

    byte[] retByte = cipher.doFinal(data);
    return new String(retByte, UTF8);
  }

  /**
   * createSecretKey
   *
   * @param key
   *        key
   * @return SecretKey
   * @throws Exception
   *         异常
   */
  protected static SecretKey createSecretKey(String key) throws Exception {
    SecretKey secretKeyObj = null;
    try {
      secretKeyObj = secretKeyFactory.generateSecret(new DESKeySpec(convertKeyIv(key)));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return secretKeyObj;
  }

  /**
   * createIvParameterSpec
   *
   * @param key
   *        key
   * @return IvParameterSpec
   * @throws Exception
   *         异常
   */
  protected static IvParameterSpec createIvParameterSpec(String key) throws Exception {
    IvParameterSpec ivObj = null;
    ivObj = new IvParameterSpec(convertKeyIv(key));
    return ivObj;
  }

  /**
   * createAuthToken
   *
   * @param uid
   *        uid
   * @param ip
   *        ip
   * @param authKey
   *        authKey
   * @param authIv
   *        authIv
   * @return String
   */
  public static String createAuthToken(String uid, String ip, String authKey, String authIv) {
    int feed = new java.util.Random().nextInt(1000);
    long timestamp = System.currentTimeMillis() / 1000;
    return createAuthToken(uid, ip, authKey, authIv, feed, timestamp);
  }

  /**
   * createAuthToken
   *
   * @param uid
   *        uid
   * @param ip
   *        ip
   * @param authKey
   *        authKey
   * @param authIv
   *        authIv
   * @param feed
   *        feed
   * @param timestamp
   *        timestamp
   * @return String
   */
  public static String createAuthToken(String uid, String ip, String authKey, String authIv,
                                       int feed, long timestamp) {
    SecretKey secretKeyObj = null;
    IvParameterSpec ivObj = null;
    try {
      secretKeyObj = (SecretKey) createSecretKey(authKey);
      ivObj = (IvParameterSpec) createIvParameterSpec(authIv);
    } catch (Exception e) {
      e.printStackTrace();
    }

    StringBuilder message = new StringBuilder(128);
    message.append(feed); // �����ʹÿ����ɵ����ƶ�û�й���
    message.append(AuthToken.FIELD_SEPERATOR).append(AUTH_TOKEN_VERSION); // ���Ƶİ汾
    message.append(AuthToken.FIELD_SEPERATOR).append(uid); // �û�ID
    message.append(AuthToken.FIELD_SEPERATOR).append(ip); // �û�IP
    message.append(AuthToken.FIELD_SEPERATOR).append(timestamp); // ʱ���

    System.err.println("=======message:" + message.toString());

    byte[] data = null;
    try {
      data = message.toString().getBytes(UTF8);
    } catch (Exception e) {
      e.printStackTrace();
    }

    byte[] authToken = null;
    try {
      authToken = encrypt(data, secretKeyObj, ivObj);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return hexToString(authToken);
  }

  /**
   * decryptAuthInfo
   *
   * @param authToken
   *        authToken
   * @param authKey
   *        authKey
   * @param authIv
   *        authIv
   * @return AuthToken
   * @throws Exception
   *         异常
   */
  public static AuthToken decryptAuthInfo(String authToken, String authKey, String authIv)
      throws Exception {
    SecretKey secretKeyObj = null;
    IvParameterSpec ivObj = null;
    try {
      secretKeyObj = (SecretKey) createSecretKey(authKey);
      ivObj = (IvParameterSpec) createIvParameterSpec(authIv);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String message = decrypt(authToken, secretKeyObj, ivObj);
    return AuthToken.parse(message);
  }

  /**
   * test
   *
   * @param args
   *        args
   * @throws Exception
   *         异常
   */
  public static void main(String[] args) throws Exception {
    String authKey = "FyLuW0cB";
    String authIv = "6hKOXLHg";
    String uid = "123456";
    InetAddress addr = InetAddress.getLocalHost();
    // 对应ip
    String uip = String.valueOf(addr.getHostAddress());

    String token = createAuthToken(uid, uip, authKey, authIv);
    System.err.println("token:" + token);
    decryptAuthInfo(token, authKey, authIv);
  }

}
