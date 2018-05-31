package com.fang.im.management.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 *
 * <p>
 * Description: StringToIntegerConverter
 * </p>
 *
 * @author jinshilei
 *         2017年2月24日
 * @version 1.0
 *
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

  /**
   * 字符串转换为整型
   *
   * @param source
   *        字符串参数
   * @return 返回整型
   */
  @Override
  public Integer convert(String source) {
    if (!StringUtils.isEmpty(source)) {
      try {
        return Integer.parseInt(source);
      } catch (Exception e) {
        System.out.println("字符串转换为Integer失败");
      }
    }
    return null;
  }
}
