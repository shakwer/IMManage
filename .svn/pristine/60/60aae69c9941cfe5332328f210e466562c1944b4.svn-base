package com.fang.im.management.web.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 *
 * <p>
 * Description: StringToDateConverter
 * </p>
 *
 * @author jinshilei
 *         2017年2月26日
 * @version 1.0
 *
 */
public class StringToDateConverter implements Converter<String, Date> {

  /**
   * 支持的时间格式字符串
   */
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * 将字符串时间转换为Date类型
   *
   * @param source
   *        字符串
   *
   * @return 时间
   */
  @Override
  public Date convert(String source) {
    if (!StringUtils.isEmpty(source)) {
      try {
        return simpleDateFormat.parse(source);
      } catch (Exception ex) {
        System.out.println("字符串转换为时间失败：" + source);
      }
    }
    return null;
  }
}
