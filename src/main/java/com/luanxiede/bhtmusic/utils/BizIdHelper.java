package com.luanxiede.bhtmusic.utils;

import org.springframework.stereotype.Component;

/**
 * @author passer-by
 * @date 2022/4/30
 */
@Component
public class BizIdHelper {
    //  常规业务标识码
    private static final String COMMON = "00";

    // 这一步也是需要优化的，目前先写死，但是整体没有影响
    private static final BizIdGenerator bizIdGenerator = new BizIdGenerator(02, 03);

    //生成一般的业务id
    public static String id() {
        return BizIdHelper.bizIdGenerator.getNextId(COMMON);
    }
}
