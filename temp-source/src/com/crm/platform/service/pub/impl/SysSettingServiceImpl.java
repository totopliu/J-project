package com.crm.platform.service.pub.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.mapper.pub.SysSettingMapper;
import com.crm.platform.service.pub.SysSettingService;

@Service
@Transactional
public class SysSettingServiceImpl implements SysSettingService {

    @Autowired
    private SysSettingMapper sysSettingMapper;

    @Override
    public int getAutoIn() {
        return sysSettingMapper.getAutoIn();
    }

    @Override
    public AjaxJson saveSysSetting(int autoIn) {
        AjaxJson json = new AjaxJson();
        sysSettingMapper.saveSysSetting(autoIn);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson updateSysConfig(String key, String value) {
        AjaxJson json = new AjaxJson();
        sysSettingMapper.updateSysConfig(key, value);
        json.setSuccess(true);
        return json;
    }

    @Override
    public String getConfigByKey(String key) {
        return sysSettingMapper.getConfigByKey(key);
    }

}
