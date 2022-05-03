package com.crm.platform.service.pub.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.pub.GroupRebateCurrencyDTO;
import com.crm.platform.entity.pub.GroupRebateDTO;
import com.crm.platform.entity.pub.GroupRebateVO;
import com.crm.platform.entity.pub.RebatePlanCurrencyDTO;
import com.crm.platform.entity.pub.RebatePlanLevelDTO;
import com.crm.platform.mapper.pub.GroupRebateMapper;
import com.crm.platform.service.pub.GroupRebateService;

@Service
@Transactional
public class GroupRebateServiceImpl implements GroupRebateService {

    @Autowired
    private GroupRebateMapper groupRebateMapper;

    @Override
    public AjaxJson save(String groupName, String levelJson) {
        AjaxJson json = new AjaxJson();
        groupRebateMapper.removeByGroupName(groupName);
        List<RebatePlanLevelDTO> rebateLevelDTOs = JSON.parseArray(levelJson, RebatePlanLevelDTO.class);
        if (rebateLevelDTOs != null && rebateLevelDTOs.size() > 0) {
            for (RebatePlanLevelDTO rebatePlanLevelDTO : rebateLevelDTOs) {
                List<RebatePlanCurrencyDTO> rebatePlanCurrencyDTOs = rebatePlanLevelDTO.getCurrencies();
                if (rebatePlanCurrencyDTOs != null && rebatePlanCurrencyDTOs.size() > 0) {
                    for (RebatePlanCurrencyDTO rebatePlanCurrencyDTO : rebatePlanCurrencyDTOs) {
                        GroupRebateVO groupRebateVO = new GroupRebateVO();
                        if (rebatePlanCurrencyDTO.getFixedRebate() != null
                                && rebatePlanCurrencyDTO.getFixedRebate().length() > 0) {
                            groupRebateVO.setGroupName(groupName);
                            groupRebateVO.setLevel(Integer.parseInt(rebatePlanLevelDTO.getLevel()));
                            groupRebateVO.setCurrencyId(Integer.parseInt(rebatePlanCurrencyDTO.getCurrencyId()));
                            groupRebateVO.setFixed(Double.parseDouble(rebatePlanCurrencyDTO.getFixedRebate()));
                            groupRebateMapper.save(groupRebateVO);
                        } else if (rebatePlanCurrencyDTO.getPointRebate() != null
                                && rebatePlanCurrencyDTO.getPointRebate().length() > 0) {
                            groupRebateVO.setGroupName(groupName);
                            groupRebateVO.setLevel(Integer.parseInt(rebatePlanLevelDTO.getLevel()));
                            groupRebateVO.setCurrencyId(Integer.parseInt(rebatePlanCurrencyDTO.getCurrencyId()));
                            groupRebateVO.setPoint(Double.parseDouble(rebatePlanCurrencyDTO.getPointRebate()));
                            groupRebateMapper.save(groupRebateVO);
                        }
                    }
                }
            }
        }
        json.setSuccess(true);
        json.setMsg(Constant.SUCCESS_SAVE);
        return json;
    }

    @Override
    public AjaxJson getConfig(String groupName) {
        AjaxJson json = new AjaxJson();
        List<GroupRebateDTO> groupRebateDTOs = new ArrayList<GroupRebateDTO>();
        List<Integer> levels = groupRebateMapper.getLevelsByGroupName(groupName);
        if (levels != null && levels.size() > 0) {
            for (Integer level : levels) {
                GroupRebateDTO groupRebateDTO = new GroupRebateDTO();
                groupRebateDTO.setLevel(level);
                List<GroupRebateCurrencyDTO> groupRebateCurrencyDTOs = groupRebateMapper
                        .getGroupRebateCurrency(groupName, level);
                groupRebateDTO.setGroupRebateCurrencyDTO(groupRebateCurrencyDTOs);
                groupRebateDTOs.add(groupRebateDTO);
            }
        }
        json.setSuccess(true);
        json.setData(groupRebateDTOs);
        return json;
    }

}
