package com.security.demo.handler.domain;

public enum BadCredentialsEnum {

    /**
     * 用户名未填写
     */
    USERNAME_IS_REQUIRED(1, "用户名未填写"),
    /**
     * 用户不存在
     */
    USER_IS_NOT_EXIST(2, "用户不存在"),
    /**
     * 用户已禁用
     */
    USER_IS_NOT_SUPPORT(3, "用户已禁用"),

    /**
     * 密码错误
     */
    BAD_CREDENTIALS(5, "密码错误"),

    /**
     * 会话过期
     */
    SESSION_EXPIRED(6, "会话过期");

    private int errorCode;
    private String msg;

    private BadCredentialsEnum(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        if (this == BAD_CREDENTIALS)
            return "Bad credentials";

        return this.name();
    }

    public static BadCredentialsEnum findBadCredentialsEnum(String name) {
        BadCredentialsEnum[] values = BadCredentialsEnum.values();

        for (BadCredentialsEnum value : values) {
            if(value.toString().equals(name))
                return value;
        }

        return null;
    }
}
