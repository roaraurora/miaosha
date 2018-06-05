package com.imooc.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {


    private int expireSeconds;

    private String prefix;

    BasePrefix(String prefix) {
        // 过期时间默认0 ,代表永不过期
        this(0,prefix);
    }

    BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }


    @Override
    public String getPrefix() {
        /* *
         * 模块 : 属性 ：值 构成整个Key
         * 如 token => MiaoshaUserKey:tk+UUID  user => UserKey:id+id
         * @param []
         * @return  key的前缀 : java.lang.String
         */
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
