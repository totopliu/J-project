package com.crm.platform.service.pub;

import com.crm.platform.entity.AjaxJson;

public interface SysSettingService {

    int getAutoIn();

    AjaxJson saveSysSetting(int autoIn);

    AjaxJson updateSysConfig(String key, String value);

    String getConfigByKey(String key);

}
