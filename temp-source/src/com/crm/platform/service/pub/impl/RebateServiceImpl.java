package com.crm.platform.service.pub.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.GoldSokectDTO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.RebateBO;
import com.crm.platform.entity.pub.RebateEntity;
import com.crm.platform.entity.pub.RebatePlanCurrencyBO;
import com.crm.platform.entity.pub.RebatePlanCurrencyDTO;
import com.crm.platform.entity.pub.RebatePlanCurrencyVO;
import com.crm.platform.entity.pub.RebatePlanDTO;
import com.crm.platform.entity.pub.RebatePlanLevelBO;
import com.crm.platform.entity.pub.RebatePlanLevelDTO;
import com.crm.platform.entity.pub.RebatePlanLevelVO;
import com.crm.platform.entity.pub.RebateTransferReviewDTO;
import com.crm.platform.entity.pub.StatisticsGoldDTO;
import com.crm.platform.mapper.pub.ManagerMapper;
import com.crm.platform.mapper.pub.RebateMapper;
import com.crm.platform.service.pub.RebateService;
import com.crm.util.MathUtil;
import com.crm.util.MsgUtil;
import com.crm.util.Mt4PropertiesUtil;
import com.crm.util.SocketUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class RebateServiceImpl implements RebateService {

    @Autowired
    private RebateMapper rebateMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public PageInfo<JSONObject> listUnderlingRebate(DataGrid grid, Integer managerId) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(rebateMapper.queryUnderlingRebateList(managerId));
    }

    @Override
    public AjaxJson saveFixed(Integer currencyId, Integer managerId, Double rebateFixed, Integer belongId) {
        AjaxJson json = new AjaxJson();
        if (managerId == null) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.PLEASE_CHOOSE_UNDERLING));
            return json;
        }
        ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(managerId);
        if (managerEntity.getRebateLogin() == null) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.REBATE_LOGIN_NULL));
            return json;
        }
        // TODO
        // int planId = managerEntity.getPlanId();
        // if (planId > 0) { // 如果为平台设置的返佣，则不可修改
        // json.setSuccess(false);
        // json.setMsg(Constant.REBATE_PLATFORM_SETTING);
        // return json;
        // }
        Integer adminCount = managerMapper.countAdmin(belongId); // 查询判断上级是否为超级管理员
        if (adminCount <= 0) { // 如果不是超级管理员
            Integer fixedCount = rebateMapper.countFixed(belongId, currencyId); // 查询判断上级是否为定值返佣
            if (fixedCount <= 0) { // 如果不是定值返佣，则返回错误
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(Constant.VIOLATION_OF_REBATE_RULES));
                return json;
            } else {
                Double belongFixed = rebateMapper.findBelongFixed(belongId, currencyId); // 查询上级定值返佣值
                if (new BigDecimal(belongFixed).compareTo(new BigDecimal(rebateFixed)) < 0) { // 如果不小于上级，则返回错误
                    json.setSuccess(false);
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.VALUE_EXCEEDS_THE_LIMIT));
                    return json;
                }
            }
        }
        Integer underlingPointCount = rebateMapper.countUnderlingPoint(managerId, currencyId); // 查询判断是否已为下级设置点值返佣
        if (underlingPointCount > 0) { // 如果已为下级设置点值返佣，则返回错误
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.VIOLATION_OF_UNDERLING_REBATE));
            return json;
        }
        Integer underlingFixedCount = rebateMapper.countUnderlingFixed(managerId, currencyId); // 查询判断是否已为下级设置定值返佣
        if (underlingFixedCount > 0) { // 如果已为下级设置定值返佣
            Double maxUnderlingFixed = rebateMapper.findMaxUnderlingFixed(managerId, currencyId); // 查询为下级设置的最大定值返佣值
            if (new BigDecimal(rebateFixed).compareTo(new BigDecimal(maxUnderlingFixed)) < 0) { // 如果小于为下级设置的最大返佣值，则返回错误
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(Constant.VALUE_LESS_THE_LIMIT));
                return json;
            }
        }
        RebateEntity rebateEntity = rebateMapper.findByCidAndMid(currencyId, managerId);
        if (rebateEntity != null) {
            rebateEntity.setRebateFixed(rebateFixed);
            rebateMapper.updFixedById(rebateEntity);
        } else {
            rebateEntity = new RebateEntity();
            rebateEntity.setCurrencyId(currencyId);
            rebateEntity.setManagerId(managerId);
            rebateEntity.setRebateFixed(rebateFixed);
            rebateMapper.insertFixed(rebateEntity);
        }
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        return json;
    }

    @Override
    public AjaxJson savePoint(Integer currencyId, Integer managerId, Double rebatePoint, Integer belongId) {
        AjaxJson json = new AjaxJson();
        if (managerId == null) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.PLEASE_CHOOSE_UNDERLING));
            return json;
        }
        // 判断是否拥有返佣账户
        ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(managerId);
        if (managerEntity.getRebateLogin() == null) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.REBATE_LOGIN_NULL));
            return json;
        }
        // TODO
        // int planId = managerEntity.getPlanId();
        // if (planId > 0) { // 如果为平台设置的返佣，则不可修改
        // json.setSuccess(false);
        // json.setMsg(Constant.REBATE_PLATFORM_SETTING);
        // return json;
        // }
        Integer adminCount = managerMapper.countAdmin(belongId); // 查询判断上级是否为超级管理员
        if (adminCount <= 0) { // 如果上级不是超级管理员
            Integer pointCount = rebateMapper.countPoint(belongId, currencyId); // 查询判断上级是否设置为点值返佣
            if (pointCount <= 0) { // 如果上级不是点值返佣
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(Constant.VIOLATION_OF_REBATE_RULES));
                return json;
            }
            int belongPoint = rebateMapper.findBelongPoint(belongId, currencyId);
            BigDecimal belongPointDecimal = new BigDecimal(belongPoint);
            BigDecimal rebatePointDecimal = new BigDecimal(rebatePoint);
            if (belongPointDecimal.compareTo(rebatePointDecimal) < 0) {
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(Constant.VALUE_EXCEEDS_THE_LIMIT));
                return json;
            }
        }
        Integer underlingFixedCount = rebateMapper.countUnderlingFixed(managerId, currencyId); // 查询判断是否已为下级设置定值返佣
        if (underlingFixedCount > 0) { // 如果已为下级设置定值返佣，则返回错误
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.VIOLATION_OF_UNDERLING_REBATE));
            return json;
        }
        Integer underlingPointCount = rebateMapper.countUnderlingPoint(managerId, currencyId); // 查询判断是否已为下级设置点值返佣
        if (underlingPointCount > 0) { // 如果已为下级设置点值返佣
            Double maxUnderlingPoint = rebateMapper.findMaxUnderlingPoint(managerId, currencyId); // 查询已为下级设置的最大返佣点值
            if (new BigDecimal(rebatePoint).compareTo(new BigDecimal(maxUnderlingPoint)) < 0) { // 如果小于为下级设置的最大返佣点值，则返回错误
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(Constant.VALUE_EXCEEDS_THE_LIMIT));
                return json;
            }
        }
        RebateEntity rebateEntity = rebateMapper.findByCidAndMid(currencyId, managerId);
        if (rebateEntity != null) {
            rebateEntity.setRebatePoint(rebatePoint);
            rebateMapper.updPointById(rebateEntity);
        } else {
            rebateEntity = new RebateEntity();
            rebateEntity.setCurrencyId(currencyId);
            rebateEntity.setManagerId(managerId);
            rebateEntity.setRebatePoint(rebatePoint);
            rebateMapper.insertPoint(rebateEntity);
        }
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        return json;
    }

    @Override
    public PageInfo<JSONObject> listSetting(DataGrid grid, Integer managerid) {
        List<JSONObject> list = managerMapper.findUnderLingTree(managerid);
        StringBuffer managerids = new StringBuffer();
        for (JSONObject jsonObject : list) {
            managerid = jsonObject.getInteger("val");
            if (managerids.length() == 0) {
                managerids.append(managerid);
            } else {
                managerids.append(",").append(managerid);
            }
            managerids.append(getUnderlingTree(managerid));
        }
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(managerMapper.queryAllUnderling(managerids.toString()));
    }

    private StringBuffer getUnderlingTree(Integer managerid) {
        StringBuffer managerids = new StringBuffer();
        List<JSONObject> list = managerMapper.findUnderLingTree(managerid);
        if (list != null && list.size() > 0) {
            for (JSONObject jsonObject1 : list) {
                managerid = jsonObject1.getInteger("val");
                managerids.append(",").append(managerid);
                managerids.append(getUnderlingTree(managerid));
            }
        }
        return managerids;
    }

    @Override
    public PageInfo<JSONObject> listManagerRebate(DataGrid grid, Integer login, String rebateDate, String ticket,
            String over) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(rebateMapper.findManagerRebateList(login, rebateDate, ticket, over));
    }

    @Override
    public double countRebateTotal(String ids) {
        return rebateMapper.countRebateTotal(ids);
    }

    @Override
    public AjaxJson saveRebateIn(String ids, String login) {
        AjaxJson json = new AjaxJson();
        double rebateTotal = rebateMapper.countRebateTotal(ids);
        // 调用入金接口返佣
        GoldSokectDTO goldSokectDTO = Mt4PropertiesUtil.getConfigGoldSoketDTO();
        goldSokectDTO.setLogin(Integer.parseInt(login));
        goldSokectDTO.setMoney(MathUtil.roundBigDecimalSetHalfupToInt(0,
                MathUtil.translateToBigDecimal(rebateTotal).multiply(new BigDecimal(100))));
        goldSokectDTO.setComment(Constant.MANUAL_REBATE);
        String response = SocketUtil.connectMt4(JSONObject.toJSONString(goldSokectDTO));
        if (!"0".equals(response)) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.IN_GOLD_FAIL));
            return json;
        }
        // 入金成功后修改返佣数据状态
        rebateMapper.updRebateOver(ids);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson rebateSumToMT4(Integer login) {
        AjaxJson json = new AjaxJson();
        Double rebateTotal = managerMapper.findRebateByLogin(login);
        if (rebateTotal == null) {
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.INSUFFICIENT_BALANCE));
            json.setSuccess(false);
            return json;
        }
        // 调用入金接口返佣
        GoldSokectDTO goldSokectDTO = Mt4PropertiesUtil.getConfigGoldSoketDTO();
        Integer rebateLogin = managerMapper.getRebateLoginByLogin(login);
        goldSokectDTO.setLogin(rebateLogin);
        goldSokectDTO.setMoney(MathUtil.roundBigDecimalSetHalfupToInt(0,
                MathUtil.translateToBigDecimal(rebateTotal).multiply(new BigDecimal(100))));
        goldSokectDTO.setComment(Constant.MANUAL_REBATE);
        String response = SocketUtil.connectMt4(JSONObject.toJSONString(goldSokectDTO));
        if (!"0".equals(response)) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.IN_GOLD_FAIL));
            return json;
        }
        // 入金成功后通过login修改返佣数据状态
        rebateMapper.updRebateOverByLogin(login);
        json.setSuccess(true);
        Double newRebateTotal = managerMapper.findRebateByLogin(login);
        if (newRebateTotal != null) {
            json.setData(MathUtil.roundDoubleSetHalfUpToStr(2, managerMapper.findRebateByLogin(login)));
        } else {
            json.setData(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return json;
    }

    @Override
    public AjaxJson countRebateTotal(Integer login, String ticket, String rebateDate, String over) {
        AjaxJson json = new AjaxJson();
        StatisticsGoldDTO statisticsGoldDTO = rebateMapper.getRebateTotal(login, ticket, rebateDate, over);
        json.setSuccess(true);
        json.setData(statisticsGoldDTO);
        return json;
    }

    @Override
    public PageInfo<JSONObject> listRebatePlan(DataGrid grid) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(rebateMapper.listRebatePlan());
    }

    @Override
    public RebatePlanDTO getRebatePlan(Integer id) {
        return rebateMapper.getRebatePlan(id);
    }

    @Override
    public AjaxJson saveRebatePlan(RebatePlanDTO dto) {
        AjaxJson json = new AjaxJson();
        if (dto.getId() != null) {
            rebateMapper.updateRebatePlan(dto);
        } else {
            rebateMapper.saveRebatePlan(dto);
        }
        json.setSuccess(true);
        json.setMsg(Constant.SUCCESS_DELETE);
        return json;
    }

    @Override
    public AjaxJson removeRebatePlans(int[] ids) {
        AjaxJson json = new AjaxJson();
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                List<RebatePlanLevelBO> rebatePlanLevelBOs = rebateMapper.listRebatePlanLevel(ids[i]);
                if (rebatePlanLevelBOs != null && rebatePlanLevelBOs.size() > 0) {
                    for (RebatePlanLevelBO rebatePlanLevelBO : rebatePlanLevelBOs) {
                        rebateMapper.removeRebatePlanCurrency(rebatePlanLevelBO.getId());
                    }
                }
                rebateMapper.removeRebatePlanLevel(ids[i]);
                rebateMapper.removeRebatePlan(ids[i]);
            }
        }
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_DELETE));
        return json;
    }

    @Override
    public AjaxJson getRebatePlanCurrency(int planId) {
        AjaxJson json = new AjaxJson();
        List<RebatePlanLevelVO> rebatePlanLevelVOs = new ArrayList<RebatePlanLevelVO>();
        List<RebatePlanLevelBO> rebatePlanLevelBOs = rebateMapper.listRebatePlanLevel(planId);
        if (rebatePlanLevelBOs != null && rebatePlanLevelBOs.size() > 0) {
            for (RebatePlanLevelBO rebatePlanLevelBO : rebatePlanLevelBOs) {
                RebatePlanLevelVO rebatePlanLevelVO = new RebatePlanLevelVO();
                rebatePlanLevelVO.setId(rebatePlanLevelBO.getId());
                rebatePlanLevelVO.setPlanId(rebatePlanLevelBO.getPlanId());
                rebatePlanLevelVO.setLevel(rebatePlanLevelBO.getLevel());
                List<RebatePlanCurrencyVO> rebatePlanCurrencyVOs = rebateMapper
                        .listRebatePlanCurrency(rebatePlanLevelBO.getId());
                rebatePlanLevelVO.setRebatePlanCurrencyVOs(rebatePlanCurrencyVOs);
                rebatePlanLevelVOs.add(rebatePlanLevelVO);
            }
        }
        json.setData(rebatePlanLevelVOs);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson saveRebatePlanCurrency(int planId, String levelJson) {
        AjaxJson json = new AjaxJson();
        List<RebatePlanLevelBO> rebatePlanLevelBOs = rebateMapper.listRebatePlanLevel(planId);
        if (rebatePlanLevelBOs != null && rebatePlanLevelBOs.size() > 0) {
            for (RebatePlanLevelBO rebatePlanLevelBO : rebatePlanLevelBOs) {
                rebateMapper.removeRebatePlanCurrency(rebatePlanLevelBO.getId());
            }
        }
        rebateMapper.removeRebatePlanLevel(planId);

        List<RebatePlanLevelDTO> rebateLevelDTOs = JSON.parseArray(levelJson, RebatePlanLevelDTO.class);
        if (rebateLevelDTOs != null && rebateLevelDTOs.size() > 0) {
            for (RebatePlanLevelDTO rebatePlanLevelDTO : rebateLevelDTOs) {

                RebatePlanLevelBO rebatePlanLevelBO = new RebatePlanLevelBO();
                rebatePlanLevelBO.setLevel(Integer.parseInt(rebatePlanLevelDTO.getLevel()));
                rebatePlanLevelBO.setPlanId(planId);
                rebateMapper.saveRebatePlanLevel(rebatePlanLevelBO);

                List<Integer> managerids = managerMapper.getManagerIdByPlanAndLevel(planId,
                        Integer.parseInt(rebatePlanLevelDTO.getLevel()));
                for (Integer managerid : managerids) {
                    rebateMapper.removeRebateSetting(managerid);
                }

                List<RebatePlanCurrencyDTO> rebatePlanCurrencyDTOs = rebatePlanLevelDTO.getCurrencies();
                if (rebatePlanCurrencyDTOs != null && rebatePlanCurrencyDTOs.size() > 0) {
                    for (RebatePlanCurrencyDTO rebatePlanCurrencyDTO : rebatePlanCurrencyDTOs) {

                        RebatePlanCurrencyBO rebatePlanCurrencyBO = new RebatePlanCurrencyBO();
                        rebatePlanCurrencyBO.setCurrencyId(Integer.parseInt(rebatePlanCurrencyDTO.getCurrencyId()));
                        rebatePlanCurrencyBO.setLevelId(rebatePlanLevelBO.getId());
                        if (rebatePlanCurrencyDTO.getFixedRebate() != null
                                && rebatePlanCurrencyDTO.getFixedRebate().length() > 0) {
                            rebatePlanCurrencyBO.setFixedRebate(Double.valueOf(rebatePlanCurrencyDTO.getFixedRebate()));
                            for (Integer managerid : managerids) {
                                RebateBO rebateBO = new RebateBO();
                                rebateBO.setCurrencyId(Integer.parseInt(rebatePlanCurrencyDTO.getCurrencyId()));
                                rebateBO.setManagerId(managerid);
                                rebateBO.setRebateFixed(Double.valueOf(rebatePlanCurrencyDTO.getFixedRebate()));
                                rebateMapper.insertRebateBO(rebateBO);
                            }
                        }
                        if (rebatePlanCurrencyDTO.getPointRebate() != null
                                && rebatePlanCurrencyDTO.getPointRebate().length() > 0) {
                            rebatePlanCurrencyBO.setPointRebate(Double.valueOf(rebatePlanCurrencyDTO.getPointRebate()));
                            for (Integer managerid : managerids) {
                                RebateBO rebateBO = new RebateBO();
                                rebateBO.setCurrencyId(Integer.parseInt(rebatePlanCurrencyDTO.getCurrencyId()));
                                rebateBO.setManagerId(managerid);
                                rebateBO.setRebatePoint(Double.valueOf(rebatePlanCurrencyDTO.getPointRebate()));
                                rebateMapper.insertRebateBO(rebateBO);
                            }
                        }
                        rebateMapper.saveRebatePlanCurrency(rebatePlanCurrencyBO);
                    }
                }
            }
        }
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        return json;
    }

    @Override
    public PageInfo<JSONObject> listTransferRebate(DataGrid grid, Integer managerid) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(rebateMapper.listTransferRebate(managerid));
    }

    @Override
    public AjaxJson saveTransferRebate(Integer managerid, String dollar) {
        AjaxJson json = new AjaxJson();
        rebateMapper.insertTransferRebate(managerid, dollar);
        json.setSuccess(true);
        return json;
    }

    @Override
    public PageInfo<JSONObject> listTransferRebateToReview(DataGrid grid) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(rebateMapper.listTransferRebateToReview());
    }

    @Override
    public RebateTransferReviewDTO getRebateTransferDTO(Integer id) {
        return rebateMapper.getRebateTransferDTO(id);
    }

    @Override
    public AjaxJson saveRebateTransferReview(Integer id, Integer state, String reason) {
        AjaxJson json = new AjaxJson();
        rebateMapper.saveRebateTransferReview(id, state, reason);
        json.setSuccess(true);
        return json;
    }

}
