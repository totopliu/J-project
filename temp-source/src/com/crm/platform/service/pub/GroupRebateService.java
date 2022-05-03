package com.crm.platform.service.pub;

import com.crm.platform.entity.AjaxJson;

public interface GroupRebateService {

    AjaxJson save(String groupName, String levelJson);

    AjaxJson getConfig(String groupName);

}
