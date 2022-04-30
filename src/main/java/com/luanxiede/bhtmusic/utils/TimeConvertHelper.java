package com.luanxiede.bhtmusic.utils;

/**
 * @author passer-by
 * @date 2022/4/30
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 这个类主要用于处理Date LocalDate LocalDateTime 三种日期格式的相互转换
 * 这三者的互相转换都会封装成现有的函数，处理LocalDateTime 到 LocalDate 的转换
 * 为什么呢？有现成的
 * LocalDateTime dg = LocalDateTime.now();
 * LocalDate ct = dg.toLocalDate();
 */
public class TimeConvertHelper {

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    /**
     * localDateTime 转换为 Date
     *
     * @param ldt
     * @return
     */
    public static Date convertLDTtoD(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZONE_ID).toInstant());
    }

    /**
     * LocalDate 转换为 Date
     *
     * @param ld
     * @return
     */
    public static Date convertLDtoD(LocalDate ld) {
        return Date.from(ld.atStartOfDay().atZone(ZONE_ID).toInstant());
    }

    /**
     * Date 转换为 LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime convertDtoLDT(Date date){
        return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * Date 转换为LocalDate
     * @param date
     * @return
     */
    public static LocalDate convertDtoLD(Date date){
        return date.toInstant().atZone(ZONE_ID).toLocalDate();
    }

    /**
     * LocalDate 转换为 LocalDateTime
     * @param ld
     * @return
     */
    public static LocalDateTime convertLDtoLDT(LocalDate ld){
        return LocalDateTime.from(ld.atStartOfDay(ZONE_ID).toLocalDateTime());
    }
}
