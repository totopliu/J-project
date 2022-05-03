package com.crm.platform.service.pub.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.pub.CustomersReportVO;
import com.crm.platform.mapper.pub.CustomersReportMapper;
import com.crm.platform.service.pub.CustomersReportService;

@Service
@Transactional
public class CustomersReportServiceImpl implements CustomersReportService {

    @Autowired
    private CustomersReportMapper customersReportMapper;

    @Override
    public AjaxJson table() {
        AjaxJson json = new AjaxJson();
        List<CustomersReportVO> vos = customersReportMapper.listAgentData();
        json.setData(vos);
        json.setSuccess(true);
        return json;
    }

}
