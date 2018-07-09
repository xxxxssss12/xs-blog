package com.xs.blog.auth.util;

import xs.blog.utils.MD5Util;

/**
 * Created by xs on 2018/7/9
 */
public class PwdUtil {
    public static boolean isEquals(String inputPwd, String realPwdEncry) {
        String encryInputPwd = MD5Util.MD5(inputPwd);
        return encryInputPwd.equals(realPwdEncry);
    }
}
