package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    //固定盐
    private static final String salt = "1a2b3c4d";

    private static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static String inputPassToFormPass(String inputPass) {
        /* *
         * 将用户输入密码inputPass转为前端返回密码formPass 用来验证
         * 使用一个固定的 与前端(common.js)约定好的盐值进行md5散列
         * @param [inputPass]
         * @return java.lang.String
         */
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String inputPass, String salt) {
        /* *
         * 将从前端接收的密码formPass转为存入数据库的密码dbPass 被 MiaoshaUserService.login()调用
         * 使用一个动态生成的盐值进行md5散列 该salt唯一对应一个user并存入数据库
         * @param [inputPass, salt]
         * @return java.lang.String
         */
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    private static String inputPassToDBPass(String input, String saltDB) {
        /* *
         * 直接将用户输入密码转为存入数据库密码 用于校验
         * @param [input, saltDB]
         * @return java.lang.String
         */
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));//d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(inputPassToDBPass("123456","1a2b3c4d" ));//b7797cce01b4b131b433b6acf4add449
    }
}
