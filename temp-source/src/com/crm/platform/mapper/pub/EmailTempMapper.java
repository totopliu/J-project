package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.pub.EmailSettingDO;
import com.crm.platform.entity.pub.EmailTempDO;

public interface EmailTempMapper {

    List<JSONObject> listEmailTemp();

    EmailTempDO getById(Integer id);

    void updateById(EmailTempDO dto);

    EmailTempDO fingByCode(@Param("code") String code);

    EmailSettingDO getEmailSetting();

    void updateEmailSetting(EmailSettingDO dto);
}
