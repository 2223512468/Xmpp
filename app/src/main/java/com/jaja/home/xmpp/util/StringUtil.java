package com.jaja.home.xmpp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：字符串工具类
 * 创建人：admin
 * 创建时间：2016/5/4 9:21
 * 修改人：admin
 * 修改时间：2016/5/4 9:21
 * 修改备注：
 */
public class StringUtil {

    /**
     * 字符串是否为空
     *
     * @param text ：要判断的字符串
     * @return ：是否为空
     */
    public static boolean isEmpty(String text) {
        return null == text || "".equals(text) || " ".equals(text) || "null".equals(text) || text.length() == 0;
    }

    /**
     * 是否是手机号码
     *
     * @return true是 false不是
     */
    public static boolean isTel(String str) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[[0-9]])|(17[0-9])||(15[0-9])|(16[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isAllNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isAllChar(String str) {
        return str.matches("^[a-zA-Z]*");
    }

    /**
     * 验证邮箱格式是否正确
     */
    public static boolean isEmail(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }

    /**
     * 比较图形验证码是相同
     *
     * @param code0
     * @param code1
     * @return
     */
    public static boolean isAuthCodeSame(String code0, String code1) {
        if (isEmpty(code0) || isEmpty(code1)) return false;
        String lowerCode0 = code0.toLowerCase();
        String lowerCode1 = code1.toLowerCase();
        return lowerCode0.equals(lowerCode1);
    }


    public static boolean isHuZhi(String s) {
        if (s.matches("\\d+(.\\d+)?")) {
            if (s.indexOf(".") > 0) {
                //System.out.println("浮点类型");
                return true;
            } else {
                // System.out.println("整形类型");
                return true;
            }
        } else {
            // System.out.println("不是数值类型");
            return false;
        }
    }

    public static String splitStr(String str) {
        return str.split("@")[0];
    }

}
