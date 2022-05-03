package com.crm.platform.mapper.pub;

import org.apache.ibatis.annotations.Param;

public interface SysSettingMapper {

    int getAutoIn();

    void saveSysSetting(int autoIn);

    void updateSysConfig(@Param("keyName") String key, @Param("value") String value);

    String getConfigByKey(@Param("keyName") String key);

}
