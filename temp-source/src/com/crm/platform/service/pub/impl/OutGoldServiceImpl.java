package com.crm.platform.service.pub.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.constant.EmailConstant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.OutGoldReviewParam;
import com.crm.platform.entity.pub.EmailSettingDO;
import com.crm.platform.entity.pub.EmailTempDO;
import com.crm.platform.entity.pub.GoldSokectDTO;
import com.crm.platform.entity.pub.GoldSummationVO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.OutGoldDTO;
import com.crm.platform.entity.pub.StatisticsGoldDTO;
import com.crm.platform.enums.ReviewEnum;
import com.crm.platform.enums.TransactionTypeEnum;
import com.crm.platform.mapper.pub.EmailTempMapper;
import com.crm.platform.mapper.pub.ManagerMapper;
import com.crm.platform.mapper.pub.OutGoldMapper;
import com.crm.platform.mapper.pub.SysSettingMapper;
import com.crm.platform.service.pub.OutGoldService;
import com.crm.util.DateFormatUtil;
import com.crm.util.MailUtils;
import com.crm.util.MathUtil;
import com.crm.util.MsgUtil;
import com.crm.util.Mt4PropertiesUtil;
import com.crm.util.SocketUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class OutGoldServiceImpl implements OutGoldService {

    private static final Logger LOG = LoggerFactory.getLogger(OutGoldServiceImpl.class);

    @Autowired
    private OutGoldMapper outGoldMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private EmailTempMapper emailTempMapper;
    @Autowired
    private SysSettingMapper sysSettingMapper;

    @Override
    public PageInfo<JSONObject> listOutGold(DataGrid grid, Integer login, Integer ticket, String outDate) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(outGoldMapper.queryOutGold(login, ticket, outDate));
    }

    @Override
    public AjaxJson countOutGoldTotal(Integer login, Integer ticket, String outDate) {
        AjaxJson json = new AjaxJson();
        StatisticsGoldDTO statisticsGoldDTO = outGoldMapper.findOutGoldTotal(login, ticket, outDate);
        statisticsGoldDTO.setProfitTotal(MathUtil.roundDoubleSetHalfUpToDouble(2, statisticsGoldDTO.getProfitTotal()));
        json.setSuccess(true);
        json.setData(statisticsGoldDTO);
        return json;
    }

    @Override
    public AjaxJson save(OutGoldDTO dto) {
        AjaxJson json = new AjaxJson();
        ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(dto.getManagerid());
        if (StringUtils.isBlank(managerEntity.getBank()) || StringUtils.isBlank(managerEntity.getBankCard())
                || StringUtils.isBlank(managerEntity.getBankCardUrl())) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.FINISH_CARD_INFO));
            return json;
        }
        if (new BigDecimal(dto.getDollar() - dto.getMoney() / dto.getOutRate()).compareTo(BigDecimal.ONE) > -1) {
            dto.setDollar(MathUtil.roundDoubleSetDownToDouble(2, dto.getMoney() / dto.getOutRate()));
        }
        if (new BigDecimal(dto.getDollar())
                .compareTo(new BigDecimal(managerMapper.findBalanceByLogin(managerEntity.getLogin()))) > 0) {
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.WITHDRAW_MORE_THAN_BALANCE));
            return json;
        }
        outGoldMapper.save(dto);
        // 2017-9-22 新增查询提醒邮件模板
        EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.OUT_REMIND);
        String emailContent = emailTempEntity.getContent();
        emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                .replace(EmailConstant.DOLLAR, MathUtil.roundDoubleSetDownStr(2, dto.getDollar()))
                .replace(EmailConstant.MONEY, MathUtil.roundDoubleSetDownStr(2, dto.getMoney()))
                .replace(EmailConstant.BANK, managerEntity.getBank())
                .replace(EmailConstant.BANK_CARD, managerEntity.getBankCard());
        // 2017-9-18 新增发送提醒审核邮件
        EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
        try {
            MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                    emailSettingEntity.getMailPassword(), sysSettingMapper.getConfigByKey("remindEmail"),
                    emailTempEntity.getTitle(), emailContent);
        } catch (Exception e) {
            LOG.error("", e);
        }
        json.setSuccess(true);
        return json;
    }

    @Override
    public PageInfo<JSONObject> listOutGoldReview(DataGrid grid, OutGoldReviewParam param) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(outGoldMapper.queryOutGoldReview(param));
    }

    @Override
    public OutGoldDTO getOutGoldById(Integer id) {
        return outGoldMapper.findOutGoldById(id);
    }

    @Override
    public AjaxJson saveReview(OutGoldDTO dto) {
        AjaxJson json = new AjaxJson();
        ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(dto.getManagerid());
        Integer transactionType = managerMapper.getTransactionTypeByManagerId(dto.getManagerid());
        OutGoldDTO outGoldEntity = outGoldMapper.findOutGoldById(dto.getId());
        if (dto.getState() == ReviewEnum.PASS.getValue()) { // 审核通过调用MT4接口出金
            double dollar = 0;
            if (transactionType == TransactionTypeEnum.BBOOK.getValue()) {
                dollar = outGoldEntity.getDollar();
                GoldSokectDTO goldSokectDTO = Mt4PropertiesUtil.getConfigGoldSoketDTO();
                goldSokectDTO.setLogin(Integer.parseInt(dto.getLogin()));
                goldSokectDTO.setMoney(MathUtil.roundBigDecimalSetHalfupToInt(0,
                        MathUtil.translateToBigDecimal(dollar).multiply(new BigDecimal(-100))));
                goldSokectDTO.setComment(Constant.OUT_GOLD + dto.getId());
                String response = SocketUtil.connectMt4(JSONObject.toJSONString(goldSokectDTO));
                if (!"0".equals(response)) {
                    json.setSuccess(false);
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.OUT_GOLD_FAIL));
                    return json;
                }
            } else if (transactionType == TransactionTypeEnum.ABOOK.getValue()) {
                dollar = Double.parseDouble(dto.getTransactionMoney());
            }
            EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.OUT_REVIEW_SUCCESS);
            String emailContent;
            String emailTitle;
            // 用户国籍为中国发送中文邮件，其他发送英文邮件
            if (managerEntity.getCountry() == 1 || managerEntity.getCountry() == null) {
                emailContent = emailTempEntity.getContent();
                emailTitle = emailTempEntity.getTitle();
            } else {
                emailContent = emailTempEntity.getEcontent();
                emailTitle = emailTempEntity.getEtitle();
            }
            emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                    .replace(EmailConstant.CREATE_TIME,
                            DateFormatUtil.format(outGoldEntity.getCreateTime(), DateFormatUtil.SCHEME_DATE_AND_TIME))
                    .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                    .replace(EmailConstant.MONEY, MathUtil.roundDoubleSetHalfUpToStr(2, dollar));
            EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting(); // 查询邮件服务器配置
            try {
                MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                        emailSettingEntity.getMailPassword(), managerEntity.getEmail(), emailTitle, emailContent); // 发送审核通过邮件
            } catch (Exception e) {
                LOG.error("", e);
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(EmailConstant.EMAIL_FAIL));
                return json;
            }
        } else {
            EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.OUT_REVIEW_FAIL);
            String emailContent;
            String emailTitle;
            // 用户国籍为中国发送中文邮件，其他发送英文邮件
            if (managerEntity.getCountry() == 1 || managerEntity.getCountry() == null) {
                emailContent = emailTempEntity.getContent();
                emailTitle = emailTempEntity.getTitle();
            } else {
                emailContent = emailTempEntity.getEcontent();
                emailTitle = emailTempEntity.getEtitle();
            }
            emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                    .replace(EmailConstant.CREATE_TIME,
                            DateFormatUtil.format(outGoldEntity.getCreateTime(), DateFormatUtil.SCHEME_DATE_AND_TIME))
                    .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                    .replace(EmailConstant.MONEY, MathUtil.roundDoubleSetHalfUpToStr(2, outGoldEntity.getDollar()))
                    .replace(EmailConstant.REASON, StringUtils.isNoneEmpty(dto.getReason()) ? dto.getReason() : "");
            EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting(); // 查询邮件服务器配置
            try {
                MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                        emailSettingEntity.getMailPassword(), managerEntity.getEmail(), emailTitle, emailContent); // 发送审核失败邮件
            } catch (Exception e) {
                LOG.error("", e);
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(EmailConstant.EMAIL_FAIL));
                return json;
            }
        }
        outGoldMapper.updReviewState(dto);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson getOutRate() {
        AjaxJson json = new AjaxJson();
        json.setData(MathUtil.roundDoubleSetHalfUpToStr(3, outGoldMapper.getOutRate()));
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson findOutGoldReviewTotal(OutGoldReviewParam param) {
        AjaxJson json = new AjaxJson();
        GoldSummationVO goldSummationVO = outGoldMapper.findOutGoldReviewTotal(param);
        json.setData(goldSummationVO);
        json.setSuccess(true);
        return json;
    }

}
