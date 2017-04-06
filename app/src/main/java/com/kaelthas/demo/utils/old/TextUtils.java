package com.kaelthas.demo.utils.old;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KaelThas.Wang on 2016/10/20.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class TextUtils {
    /**
     * @category 判断文本是否为null或者长度为0
     */
    public static boolean isEmpty(String text) {
        if (text != null) {
            if ("".equals(text)) {
                return true;
            } else {
                if (text.length() > 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0 || isEquals("null", str));
    }


    public static boolean isEquals(String actual, String expected) {
        return ObjectUtils.isEquals(actual, expected);
    }

    public static boolean notEmpty(String text) {
        return !isEmpty(text);
    }

    /**
     * @param list
     * @return String
     * @category String列表转String
     */
    public static String ListToString(List<String> list) {
        String res = "";

        for (String str : list) {
            res += "" + str + ";";
        }
        return res;
    }

    /**
     * 判断手机号
     *
     * @param phoneNumber 手机号 15....
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|14|15|17|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
                + "(^0[3-9] {1}d{2}-?d{7,8}$)|"
                + "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
                + "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断email格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
