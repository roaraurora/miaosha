package com.imooc.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix {

    //token的有效期两天
    private static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private MiaoshaUserKey(int tokenExpire, String prefix) {
        //访问权限设置为private 防止被其他类实例化
        super(tokenExpire, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk" );
}
