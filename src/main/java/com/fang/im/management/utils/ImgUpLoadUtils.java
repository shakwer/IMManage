package com.fang.im.management.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 图片上传类
 *
 * @author YZT_SouFun
 */
public class ImgUpLoadUtils {

  /**
   * 获取请求结果
   *
   * @param request
   *        请求
   * @return HashMap<String, String>
   */
  public static HashMap<String, String> uploadImg(HttpServletRequest request) {
    HashMap<String, String> param = new HashMap<String, String>();
    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    List<FileItem> items = null;
    try {
      upload.setHeaderEncoding("utf-8");
      items = upload.parseRequest(request);
    } catch (FileUploadException e) {
      e.printStackTrace();
    }
    Iterator<FileItem> iter = items.iterator();
    while (iter.hasNext()) {
      FileItem item = (FileItem) iter.next();

      param.putAll(processUploadedFile(item));

      /*
       * if (item.isFormField()) {
       * // form表单
       * param.putAll(processFormField(item));
       * } else {
       * // file
       * param.putAll(processUploadedFile(item));
       * }
       */
    }
    return param;
  }

  /**
   * 文件上传请求
   *
   * @param item
   *        子节点
   * @return HashMap<String, String>
   */
  private static HashMap<String, String> processUploadedFile(FileItem item) {
    if (item == null) {
      return null;
    }
    HashMap<String, String> rs = new HashMap<String, String>();
    String fieldName = item.getFieldName();
    InputStream istream = null;
    String filetype = null;
    try {
      if (item.getSize() > 0) {
        istream = item.getInputStream();
      }
      filetype = item.getContentType();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String imgUrl = "";
    if (istream != null
        && filetype != null
        && (filetype.trim().equals("image/png") || filetype.trim().equals("image/jpeg")
            || filetype.trim().equals("image/pjpeg") || filetype.trim().equals("image/gif"))) {
      imgUrl = uploadImg(istream, null);
    }
    if (imgUrl != null && !imgUrl.equals("null")) {
      rs.put(fieldName, imgUrl);
    } else {
      rs.put(fieldName, "");
    }
    return rs;
  }

  /**
   * 文件流上传请求
   *
   * @param in
   *        文件流
   * @param filename
   *        文件名
   * @return String
   *         url
   */
  public static String uploadImg(InputStream in, String filename) {
    // 生成简单token
    String token = "";
    try {
      String authKey = "gi1o2tKE";
      String authIv = "aR24ThWO";
      String uid = "yuezitao";
      InetAddress addr = InetAddress.getLocalHost();
      // 获取ip
      String uip = String.valueOf(addr.getHostAddress());

      token = AuthTokenFactory.createAuthToken(uid, uip, authKey, authIv);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String urlStr = "http://img1u.fang.com/upload/pic2?isflash=y&channel=qun.avatar&city=&t="
        + token + "&uid=yuezitao";
    String res = null;
    HttpURLConnection conn = null;
    String boundary = "------------------------------" + System.currentTimeMillis();
    try {
      URL url = new URL(urlStr);
      conn = (HttpURLConnection) url.openConnection();
      conn.setConnectTimeout(5000);
      conn.setReadTimeout(30000);
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Accept", "*/*");
      conn.setRequestProperty("Connection", "Keep-Alive");
      conn.setRequestProperty("Accept-Language", "zh-CN");
      conn.setRequestProperty("Origin", "http://communitymgr.im.fang.com");
      conn.setRequestProperty("User-Agent",
          " Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
      conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
      OutputStream stream = new DataOutputStream(conn.getOutputStream());

      // file
      String contentType = "application/octet-stream";
      String name = "file";
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
      strBuf.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filename
          + "\"\r\n");
      strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
      stream.write(strBuf.toString().getBytes());
      int bytes = 0;
      byte[] bufferOut = new byte[1024];
      while ((bytes = in.read(bufferOut)) != -1) {
        stream.write(bufferOut, 0, bytes);
      }
      in.close();
      byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
      stream.write(endData);
      stream.flush();
      stream.close();
      StringBuffer sb = new StringBuffer();
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line = null;
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
        res = sb.toString();
      }
      reader.close();
      reader = null;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        conn.disconnect();
        conn = null;
      }
    }
    return res;
  }
}
