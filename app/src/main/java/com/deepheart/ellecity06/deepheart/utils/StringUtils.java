package com.deepheart.ellecity06.deepheart.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ellecity06
 * @time 2018/6/1 10:52
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.utils
 * @des TODO
 */

public class StringUtils {
    /**
     * 判断是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhoneNum(String mobiles) {
        if (mobiles.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile("^(1[0-9][0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles.trim());
        return m.matches();
    }

    /**
     * @param s
     * @return 判断字符串是否为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

}
