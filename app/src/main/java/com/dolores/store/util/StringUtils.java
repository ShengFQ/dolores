package com.dolores.store.util;

/**
 * Created by sheng on 18/4/20.
 */

public class StringUtils {

    // 判断字符串是否为空
    public static boolean isBlank(StringBuffer value) {

        return isBlank(value == null ? null : value.toString());

    }

    // 判断字符串是否为空
    public static boolean isBlank(String value) {
        if (value == null || value.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isAllNotBlank(String... values) {
        for (String value : values) {
            if (isBlank(value)) {
                return false;
            }

        }

        return true;

    }
}
