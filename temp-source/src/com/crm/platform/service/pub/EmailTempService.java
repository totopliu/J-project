package com.crm.platform.service.pub;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.EmailSettingDO;
import com.crm.platform.entity.pub.EmailTempDO;
import com.github.pagehelper.PageInfo;

public interface EmailTempService {

    PageInfo<JSONObject> listEmailTemp(DataGrid grid);

    EmailTempDO getById(Integer id);

    AjaxJson save(EmailTempDO dto);

    EmailSettingDO getEmailSetting();

    AjaxJson saveSetting(EmailSettingDO dto);

}
