package com.imooc.ad.uitls;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.imooc.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static LocalDateTime stringToLocalDateTime(String dateString) throws AdException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return LocalDateTimeUtil.parse(dateString, formatter);
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) throws AdException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.format(localDateTime);
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }
}
