package com.example.devlibrary.utils;

import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * Returns a formatted string using the specified locale, format string,
     * and arguments.
     *
     * @param format format string
     * @param args Arguments referenced by the format specifiers in the format
     *         string.
     * @return A formatted string
     */
    public static String format(String format, Object... args) {
        return new Formatter(Locale.CHINA).format(format, args).toString();
    }

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        return !(value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim()));
    }

    /**
     * 判断字符串是否是邮箱
     *
     * @param email email
     * @return 字符串是否是邮箱
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
                "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断手机号字符串是否合法
     *
     * @param phoneNumber 手机号字符串
     * @return 手机号字符串是否合法
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^1[3|4|5|7|8]\\d{9}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
