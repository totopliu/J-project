package com.crm.platform.cache;

import com.crm.framework.redis.RedisOperator;
import com.crm.util.spring.AppContextUtil;

/**
 * @ClassName: SecondLevelCached
 * @Description:
 */
public class SecondLevelCached {
    public static final int CACHED_DB_INDEX = 1;
    public static final String SYS_MANAGER_KEY = "sys:manager:";

    public static final int CACHED_DB_INDEX_CODE_GENERATOR = 2;
    public static final String SYS_MANAGER_KEY_GENERATOR = "code:generator";
    public static final String SYS_MANAGER_LONG_ID = "code:generator:id";

    public static RedisOperator getRedisClient() {
        return (RedisOperator) AppContextUtil.getBean("crmRedisClient");
    }

}
