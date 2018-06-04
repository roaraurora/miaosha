package com.imooc.miaosha.result;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }
    /* *
     * 成功的时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }


    /* *
     * 失败的时候的调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
         return new Result<>(codeMsg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
