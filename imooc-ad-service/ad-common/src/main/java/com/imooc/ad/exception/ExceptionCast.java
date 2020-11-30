package com.imooc.ad.exception;


/**
 * @author mjh
 */
public class ExceptionCast {
    /**
     * 使用此静态方法抛出自定义异常
     * @param message 抛出信息
     */
    public static void cast(String message) {
        throw new AdException(message);
    }
}

