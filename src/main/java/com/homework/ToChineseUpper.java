package com.homework;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 将阿拉伯数字转化为银行大写汉字
 *
 * @author ouyangle
 */
public class ToChineseUpper {

    /**
     * 数字对应的中文大写
     */
    private static final List<Character> chineseUpperList = Lists.newArrayList('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');

    private static final List<Character> numberUnitList = Lists.newArrayList('拾', '佰', '仟', '万', '亿');

    /**
     * 最终的字符串
     */
    private static final String POSITIVE_LAST_STRING = "%s圆整";

    private static final String NEGATIVE_LAST_STRING = "负%s圆整";

    /**
     * 获取最终的大写数字（区分正反）
     *
     * @param numStr
     * @return
     */
    public String getChineseUpper(String numStr) {
        if (numStr.charAt(0) == '-') {
            int length = numStr.length();
            numStr = filterString(toChineseUpper(numStr.substring(1, length)));
            return String.format(NEGATIVE_LAST_STRING, numStr);
        } else {
            numStr = filterString(toChineseUpper(numStr));
            return String.format(POSITIVE_LAST_STRING, numStr);
        }
    }

    /**
     * 直接将数字转化成大写数字
     *
     * @param numStr
     * @return
     */
    public String toChineseUpper(String numStr) {
        if (StringUtils.isBlank(numStr)) {
            return null;
        }
        StringBuilder upperString = new StringBuilder();
        int unitIndex = 0;
        boolean loopTime = true;
        for (int index = numStr.length(); index > 0; index--) {
            upperString.append(chineseUpperList.get(Integer.parseInt(numStr.substring(index - 1, index))));
            //拾、佰、仟、万｜拾、佰、仟、亿  两套单位循环走
            if (unitIndex % 4 == 3) {
                if (loopTime) {
                    upperString.append(numberUnitList.get(unitIndex++));
                } else {
                    upperString.append(numberUnitList.get(++unitIndex));
                }
                loopTime = !loopTime;
                unitIndex = 0;
            } else {
                upperString.append(numberUnitList.get(unitIndex++));
            }
        }
        return upperString.reverse().toString();
    }

    /**
     * 过滤不合法字符
     *
     * @param numberString
     * @return
     */
    public String filterString(String numberString) {
        if (StringUtils.isBlank(numberString)) {
            return null;
        }
        StringBuilder upperString = new StringBuilder(numberString);
        //删除首位的单位
        Character first = upperString.charAt(0);
        if (numberUnitList.contains(first)) {
            upperString.delete(0, 1);
        }
        //第一次循环，删除所有0后面的单位，单位亿需要保留
        for (int index = 0; index < upperString.length(); index++) {
            Character cur = upperString.charAt(index);
            if (index + 1 < upperString.length()) {
                Character next = upperString.charAt(index + 1);
                Boolean deleteContion = false;
                if (cur.equals(chineseUpperList.get(0)) && index < upperString.length() - 1) {
                    if (upperString.length() > 9) {
                        String nextString = upperString.substring(index);
                        if (nextString.contains(String.valueOf(numberUnitList.get(4)))) {
                            if (next.equals(numberUnitList.get(4)) | next.equals(numberUnitList.get(3))) {
                                continue;
                            }
                            deleteContion = true;
                        } else {
                            deleteContion = true;
                        }
                    } else {
                        deleteContion = true;
                    }
                }
                if (deleteContion) {
                    upperString.delete(index + 1, index + 2);
                }
            }
        }
        //第二次循环，删除所有连续0
        for (int index = 0; index < upperString.length(); index++) {
            Character cur = upperString.charAt(index);
            if (index + 1 < upperString.length()) {
                //如果不是最后一位，获取下一位字符
                Character next = upperString.charAt(index + 1);
                //删除连续0，直到下一位不是0或者没有下一位或者下一位不是单位
                while (cur.equals(chineseUpperList.get(0)) && (cur.equals(next) | numberUnitList.contains(next))) {
                    if (index + 1 == upperString.length()) {
                        //此时代表cur是最后一位0
                        next = null;
                    } else {
                        upperString.delete(index, index + 1);
                        //如果还有下一位，获取它，没有置为null
                        if (index + 1 < upperString.length()) {
                            cur = next;
                            next = upperString.charAt(index + 1);
                        } else {
                            next = null;
                        }
                    }
                }
            }
        }
        //最后一位如果是零，删除掉
        if (chineseUpperList.get(0).equals(upperString.charAt(upperString.length() - 1))) {
            upperString.delete(upperString.length() - 1, upperString.length());
        }
        return upperString.toString();
    }

    public static void main(String[] args) {
        String number_1 = "1211024234";
        String number_2 = "10000";
        String number_3 = "1000000000400";
        String number_4 = "10000000000400";
        String number_5 = "-10000000000400";
        String upperString_1 = new ToChineseUpper().getChineseUpper(number_1);
        String upperString_2 = new ToChineseUpper().getChineseUpper(number_2);
        String upperString_3 = new ToChineseUpper().getChineseUpper(number_3);
        String upperString_4 = new ToChineseUpper().getChineseUpper(number_4);
        String upperString_5 = new ToChineseUpper().getChineseUpper(number_5);
        Assert.isTrue("壹拾贰亿壹仟壹佰零贰万肆仟贰佰叁拾肆圆整".equals(upperString_1), "与预期不符合1");
        Assert.isTrue("壹万圆整".equals(upperString_2), "与预期不符合2");
        Assert.isTrue("壹万亿零肆佰圆整".equals(upperString_3), "与预期不符合3");
        Assert.isTrue("壹拾万亿零肆佰圆整".equals(upperString_4), "与预期不符合4");
        Assert.isTrue("负壹拾万亿零肆佰圆整".equals(upperString_5), "与预期不符合5");
    }
}
