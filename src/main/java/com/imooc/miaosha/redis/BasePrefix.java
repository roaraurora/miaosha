package com.imooc.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {


    private int expireSeconds;

    private String prefix;

    BasePrefix(String prefix) {
        // 过期时间默认0 ,代表永不过期
        this(0,prefix);
    }

    private BasePrefix(int expireSeconds, String prefix) {
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
         * 模块 : 属性 ：值 构成整个前缀
         * @param []
         * @return java.lang.String
         */
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
