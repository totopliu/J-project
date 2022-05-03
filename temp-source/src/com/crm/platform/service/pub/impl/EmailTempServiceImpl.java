package com.crm.platform.service.pub.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.EmailSettingDO;
import com.crm.platform.entity.pub.EmailTempDO;
import com.crm.platform.mapper.pub.EmailTempMapper;
import com.crm.platform.service.pub.EmailTempService;
import com.crm.util.MsgUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class EmailTempServiceImpl implements EmailTempService {

    @Autowired
    private EmailTempMapper emailTempMapper;

    @Override
    public PageInfo<JSONObject> listEmailTemp(DataGrid grid) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(emailTempMapper.listEmailTemp());
    }

    @Override
    public EmailTempDO getById(Integer id) {
        return emailTempMapper.getById(id);
    }

    @Override
    public AjaxJson save(EmailTempDO dto) {
        AjaxJson json = new AjaxJson();
        emailTempMapper.updateById(dto);
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        return json;
    }

    @Override
    public EmailSettingDO getEmailSetting() {
        return emailTempMapper.getEmailSetting();
    }

    @Override
    public AjaxJson saveSetting(EmailSettingDO dto) {
        AjaxJson json = new AjaxJson();
        emailTempMapper.updateEmailSetting(dto);
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        return json;
    }

}
