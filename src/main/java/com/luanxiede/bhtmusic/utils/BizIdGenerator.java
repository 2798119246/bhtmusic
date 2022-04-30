package com.luanxiede.bhtmusic.utils;

/**
 * @author passer-by
 * @date 2022/4/30
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用于生成业务id
 * 2109291321 0203 00 00000003
 * 10位的年月日时分 + 2位的数据中心id + 2位的作者id +  2位的业务标识码+8位的计数值（序列号）
 * <p>
 * 数据中心id和平台id这里就是凑位数的没有什么实际意义，其实也是留作扩展位
 */
@Slf4j
public class BizIdGenerator {
    // 计数器所占的位数
    private static final int COUNT_BITS = 8;
    //支持的最大计数，99999999
    private static final int MAX_COUNT = (int) Math.pow(10, COUNT_BITS) - 1;
    //业务计数器映射
    private static final Map<String, BizCount> bizCountM = new ConcurrentHashMap<>();
    // 计数器格式，固定为8位
    private static final String COUNT_FORMAT = "%0" + COUNT_BITS + "d";
    // 数据中心id格式 固定为2位
    private static final String DATACENTER_ID_FORMAT = "%02d";
    // 工作者id格式 固定为2位
    private static final String WORKED_ID_FORMAT = "%02d";
    //数据中心id
    private final String dataCenterId;
    //平台id
    private final String workerId;
    //平台id
    private final String platformId;

    /**
     * 构造函数，主要是要生成platformId
     *
     * @param dataCenterId
     * @param workerId
     */
    public BizIdGenerator(int dataCenterId, int workerId) {
        if (dataCenterId > 99 || dataCenterId < 1) {
            throw new IllegalArgumentException("dataCenterId 不能超过 99 或者小于1");
        }
        if (workerId > 99 || workerId < 1) {
            throw new IllegalArgumentException(String.format("workedId 不能大于99 或者小于1"));
        }
        this.dataCenterId = String.format(DATACENTER_ID_FORMAT, dataCenterId);
        this.workerId = String.format(WORKED_ID_FORMAT, workerId);
        this.platformId = this.dataCenterId + this.workerId;
        log.info("初始化平台id", platformId);
    }

    /**
     * 获取下一个业务id
     *
     * @param bizMark 业务标识码
     * @return 业务id
     */
    public String getNextId(String bizMark) {
        if (StringUtils.isBlank(bizMark)) {
            throw new RuntimeException("BizMark is bloc.Refusing to generate id");
        }
        BizCount bizCount = bizCountM.get(bizMark);
        if (bizCount == null) {
            bizCount = bizCountM.computeIfAbsent(bizMark, key ->
                    new BizCount(platformId, bizMark, new AtomicInteger(0), LocalDate.now())
            );
        }
        String bizId = bizCount.getNextId();
        log.debug("id生成器生成id：", bizId);
        return bizId;
    }

    @Data
    @AllArgsConstructor
    private class BizCount {
        /**
         * 平台编码
         */
        private String platformId;

        /**
         * 业务标识
         */
        private String bizMark;

        /**
         * 计数器
         */
        private AtomicInteger count;

        /**
         * 计数器日期
         */
        private LocalDate countDate;

        /**
         * 获取下一个业务id
         *
         * @return
         * @Throws RuntimeException 当系统时钟回退时或计算值超过最大值时抛出
         */
        public String getNextId() {
            LocalDateTime currentDateTime;
            int val;
            synchronized (this) {
                currentDateTime = LocalDateTime.now();
                LocalDate currentDate = currentDateTime.toLocalDate();
                if (this.countDate.isAfter(currentDate)) {
                    throw new RuntimeException(
                            String.format("系统时钟可能被回退，拒绝为 %s 生成id", currentDate)
                    );
                }

                if (this.countDate.isBefore(currentDate)) {
                    count.set(0);
                    this.countDate = currentDate;
                }
                val = count.incrementAndGet();
            }
            if (val > MAX_COUNT) {
                throw new RuntimeException(
                        String.format("计数不能超过 %d.拒绝生成id", MAX_COUNT)
                );
            }
            Date date = TimeConvertHelper.convertLDTtoD(currentDateTime);
            return DateFormatUtils.format(date, "yyMMddHHmm") +
                    this.platformId +
                    this.bizMark +
                    String.format(COUNT_FORMAT, val);
        }
    }

}
