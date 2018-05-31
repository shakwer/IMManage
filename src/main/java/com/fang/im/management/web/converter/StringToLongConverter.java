package com.fang.im.management.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 *
 * <p>
 * Description: StringToLongConverter
 * </p>
 *
 * @author jinshilei
 *         2017年2月8日
 * @version 1.0
 *
 */
public class StringToLongConverter implements Converter<String, Long> {

  /**
   * 字符串转换为长整型
   *
   * @param source
   *        字符串参数
   * @return 返回long类型
   */
  @Override
  public Long convert(String source) {
    if (!StringUtils.isEmpty(source)) {
      try {
        return Long.parseLong(source);
      } catch (Exception e) {
        System.out.println("String to Long转换失败");
      }
    }
    return null;
  }
}
