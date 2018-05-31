package com.fang.im.management.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 *
 * <p>
 * Description: StringTrimConverter
 * </p>
 *
 * @author jinshilei
 *         2017年2月8日
 * @version 1.0
 *
 */
public class StringTrimConverter implements Converter<String, String> {

  /**
   * 字符串去掉前后空格
   *
   * @param source
   *        字符串参数
   *
   * @return 返回去掉空格的字符串
   */
  @Override
  public String convert(String source) {
    if (source != null) {
      source = source.trim();
      if (!StringUtils.isEmpty(source)) {
        return source;
      }
    }
    return null;
  }

}
