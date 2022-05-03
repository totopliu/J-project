package com.crm.platform.service.pub.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.crm.platform.mapper.pub.*;
import com.crm.util.*;
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
import com.crm.platform.entity.pub.EmailSettingDO;
import com.crm.platform.entity.pub.EmailTempDO;
import com.crm.platform.entity.pub.GoldSokectDTO;
import com.crm.platform.entity.pub.GoldSummationVO;
import com.crm.platform.entity.pub.InGoldBO;
import com.crm.platform.entity.pub.InGoldReviewDTO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.PayResult;
import com.crm.platform.entity.pub.PayResultShenfu;
import com.crm.platform.entity.pub.StatisticsGoldDTO;
import com.crm.platform.enums.InGoldAutoReviewEnum;
import com.crm.platform.enums.PayStateEnum;
import com.crm.platform.enums.ReviewEnum;
import com.crm.platform.enums.TransactionTypeEnum;
import com.crm.platform.service.pub.InGoldService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class InGoldServiceImpl implements InGoldService {

    private static final Logger LOG = LoggerFactory.getLogger(InGoldServiceImpl.class);

    @Autowired
    private InGoldMapper inGoldMapper;

    @Autowired
    private EmailTempMapper emailTempMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private SysSettingMapper sysSettingMapper;

    @Autowired
    private ExchangeMapper exchangeMapper;

    @Override
    public PageInfo<JSONObject> listInGold(DataGrid grid, Integer login, Integer ticket, String inDate) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(inGoldMapper.listInGold(login, ticket, inDate));
    }

    @Override
    public AjaxJson countInGoldTotal(Integer login, Integer ticket, String inDate) {
        AjaxJson json = new AjaxJson();
        StatisticsGoldDTO statisticsGoldDTO = inGoldMapper.findInGoldTotal(login, ticket, inDate);
        statisticsGoldDTO.setProfitTotal(MathUtil.roundDoubleSetHalfUpToDouble(2, statisticsGoldDTO.getProfitTotal()));
        json.setSuccess(true);
        json.setData(statisticsGoldDTO);
        return json;
    }

    @Override
    public AjaxJson save(InGoldBO dto) {
        AjaxJson json = new AjaxJson();
        boolean tag = new BigDecimal(dto.getDollar()).compareTo(BigDecimal.ZERO) > 0;
        if (!tag) {
            json.setSuccess(false);
            return json;
        }
        dto.setMoney(MathUtil.roundDoubleSetDownToDouble(2, dto.getMoney()));
        if (new BigDecimal(dto.getDollar() - dto.getMoney() / dto.getRate()).compareTo(BigDecimal.ONE) > -1) {
            dto.setDollar(MathUtil.roundDoubleSetDownToDouble(2, dto.getMoney() / dto.getRate()));
        }
        if (dto.getPayType() == 4) {
            dto.setState(3);
            ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(dto.getManagerid());
            // 线下支付发送入金提醒邮件
            EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.IN_REMIND);
            String emailContent = emailTempEntity.getContent();
            emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                    .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                    .replace(EmailConstant.DOLLAR, MathUtil.roundDoubleSetDownStr(2, dto.getDollar()))
                    .replace(EmailConstant.MONEY, MathUtil.roundDoubleSetDownStr(2, dto.getMoney()));
            EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
            try {
                MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                        emailSettingEntity.getMailPassword(), sysSettingMapper.getConfigByKey("remindEmail"),
                        emailTempEntity.getTitle(), emailContent);
            } catch (Exception e) {
                LOG.error("线下支付发送邮件提醒异常", e);
            }
        } else {
            String uid = "11";
            double ran = Math.random() * 9 + 1;
            int len = (5 - uid.length());
            for (int i = 0; i < len; i++) {
                ran = ran * 10;
            }
            dto.setOrderNumber(uid + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (int) (ran));
            dto.setState(0);
        }
        inGoldMapper.save(dto);
        json.setData(dto);
        json.setSuccess(true);
        return json;
    }

    @Override
    public PageInfo<JSONObject> queryInGoldReview(DataGrid grid, InGoldReviewDTO inGoldReviewDTO) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<>(inGoldMapper.queryInGoldReview(inGoldReviewDTO));
    }

    @Override
    public InGoldBO getInGoldById(Integer id) {
        return inGoldMapper.findInGoldById(id);
    }

    @Override
    public AjaxJson saveReview(InGoldBO dto) {
        AjaxJson json = new AjaxJson();
        InGoldBO inGoldEntity = inGoldMapper.findInGoldById(dto.getId());
        if (inGoldEntity.getState() == 1 || inGoldEntity.getState() == 2) {
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.IN_GOLD_REVIEWED));
            return json;
        }
        ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(dto.getManagerid());
        if (dto.getState() == ReviewEnum.PASS.getValue()) { // 审核通过，调用MT4入金接口
            double dollar = 0;
            if (dto.getTransactionType() == TransactionTypeEnum.BBOOK.getValue()) {
                GoldSokectDTO goldSocketDTO = Mt4PropertiesUtil.getConfigGoldSoketDTO();
                goldSocketDTO.setLogin(Integer.parseInt(dto.getLogin()));
                goldSocketDTO.setMoney(MathUtil.roundBigDecimalSetHalfupToInt(0,
                        MathUtil.translateToBigDecimal(dto.getDollar()).multiply(new BigDecimal(100))));
                goldSocketDTO.setComment(Constant.IN_GOLD + dto.getId());
                String response = SocketUtil.connectMt4(JSONObject.toJSONString(goldSocketDTO));
                if (!"0".equals(response)) { // 如果接口返回0为失败
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.IN_GOLD_FAIL));
                    return json;
                }
                dollar = inGoldEntity.getDollar();
            } else if (dto.getTransactionType() == TransactionTypeEnum.ABOOK.getValue()) {
                dollar = Double.parseDouble(dto.getTransactionDollar());
            }
            EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.IN_REVIEW_SUCCESS);
            String emailContent;
            String emailTitle;
            if (managerEntity.getCountry() == 1 || managerEntity.getCountry() == null) {
                emailContent = emailTempEntity.getContent();
                emailTitle = emailTempEntity.getTitle();
            } else {
                emailContent = emailTempEntity.getEcontent();
                emailTitle = emailTempEntity.getEtitle();
            }
            emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                    .replace(EmailConstant.CREATE_TIME,
                            DateFormatUtil.format(inGoldEntity.getCreateTime(), DateFormatUtil.SCHEME_DATE_AND_TIME))
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
            EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.IN_REVIEW_FAIL);
            String emailContent;
            String emailTitle;
            if (managerEntity.getCountry() == 1 || managerEntity.getCountry() == null) {
                emailContent = emailTempEntity.getContent();
                emailTitle = emailTempEntity.getTitle();
            } else {
                emailContent = emailTempEntity.getEcontent();
                emailTitle = emailTempEntity.getEtitle();
            }
            emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                    .replace(EmailConstant.CREATE_TIME,
                            DateFormatUtil.format(inGoldEntity.getCreateTime(), DateFormatUtil.SCHEME_DATE_AND_TIME))
                    .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                    .replace(EmailConstant.MONEY, MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getDollar()))
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
        inGoldMapper.updReviewState(dto);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson getRate() {
        AjaxJson json = new AjaxJson();
        json.setSuccess(true);
        json.setData(inGoldMapper.findRate());
        return json;
    }

    @Override
    public void updateInGoldByResult(PayResult payResult) {
        if (payResult != null) {
            if ("000".equals(payResult.getTranstat())) {
                String inGoldId = payResult.getOrderid();
                InGoldBO inGoldEntity = inGoldMapper.findInGoldById(Integer.parseInt(inGoldId));
                BigDecimal resultAmount = new BigDecimal(payResult.getAmount());
                BigDecimal money = new BigDecimal(inGoldEntity.getMoney());
                if (resultAmount.compareTo(money) == 0) {
                    inGoldEntity.setState(3);
                    inGoldMapper.updReviewStateByResult(inGoldEntity);
                }
            }
        }
    }

    @Override
    public void checkResultShenfu(PayResultShenfu result) {
        if (result != null) {
            if ("00".equals(result.getRespCode())) {
                String inGoldId = result.getOrderNo();
                InGoldBO inGoldEntity = inGoldMapper.findInGoldById(Integer.parseInt(inGoldId));
                if (inGoldEntity.getState() == 0) {
                    BigDecimal resultAmount = new BigDecimal(result.getTxnAmt());
                    BigDecimal money = new BigDecimal(inGoldEntity.getMoney());
                    if (resultAmount.compareTo(money) == 0) {
                        inGoldEntity.setState(3);
                        inGoldMapper.updReviewStateByResult(inGoldEntity);
                    }
                }
            }
        }
    }

    @Override
    public void checkResultDinpay(String order_no, String ipsbillno) {
        InGoldBO inGoldEntity = inGoldMapper.findInGoldById(Integer.parseInt(order_no));
        inGoldEntity.setOrderNumber(ipsbillno);
        if (inGoldEntity.getState() == PayStateEnum.UNPAY.getValue()) {
            Integer transactionType = managerMapper.getTransactionTypeByManagerId(inGoldEntity.getManagerid());
            ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(inGoldEntity.getManagerid());
            if (sysSettingMapper.getAutoIn() == InGoldAutoReviewEnum.AUTO.getValue()
                    && transactionType == TransactionTypeEnum.BBOOK.getValue()) {
                GoldSokectDTO goldSocketDTO = Mt4PropertiesUtil.getConfigGoldSoketDTO();
                goldSocketDTO.setLogin(Integer.parseInt(inGoldEntity.getLogin()));
                goldSocketDTO.setMoney(MathUtil.roundBigDecimalSetHalfupToInt(0,
                        MathUtil.translateToBigDecimal(inGoldEntity.getDollar()).multiply(new BigDecimal(100))));
                goldSocketDTO.setComment(Constant.IN_GOLD + inGoldEntity.getId());
                String response = SocketUtil.connectMt4(JSONObject.toJSONString(goldSocketDTO));
                if ("0".equals(response)) {
                    EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.IN_REVIEW_SUCCESS);
                    String emailContent = emailTempEntity.getContent();
                    emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                            .replace(EmailConstant.CREATE_TIME,
                                    DateFormatUtil.format(inGoldEntity.getCreateTime(),
                                            DateFormatUtil.SCHEME_DATE_AND_TIME))
                            .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                            .replace(EmailConstant.MONEY,
                                    MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getDollar()));
                    EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting(); // 查询邮件服务器配置
                    try {
                        MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                                emailSettingEntity.getMailPassword(), managerEntity.getEmail(),
                                emailTempEntity.getTitle(), emailContent); // 发送审核通过邮件
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    inGoldEntity.setState(PayStateEnum.PASS.getValue());
                    inGoldMapper.updReviewState(inGoldEntity);
                }
            } else {
                inGoldEntity.setState(PayStateEnum.PAY.getValue());
                inGoldMapper.updReviewStateByResult(inGoldEntity);
                // 2017-9-22 新增查询提醒邮件模板
                EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.IN_REMIND);
                String emailContent = emailTempEntity.getContent();
                emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                        .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                        .replace(EmailConstant.DOLLAR, MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getDollar()))
                        .replace(EmailConstant.MONEY, MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getMoney()));
                // 2017-9-18 新增发送提醒审核邮件
                EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
                try {
                    MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                            emailSettingEntity.getMailPassword(), sysSettingMapper.getConfigByKey("remindEmail"),
                            emailTempEntity.getTitle(), emailContent);
                } catch (Exception e) {
                    LOG.error("", e);
                }
            }
        }
    }

    @Override
    public AjaxJson findInGoldReviewTotal(InGoldReviewDTO inGoldReviewDTO) {
        AjaxJson json = new AjaxJson();
        GoldSummationVO goldSummationVO = inGoldMapper.findInGoldReviewTotal(inGoldReviewDTO);
        json.setSuccess(true);
        json.setData(goldSummationVO);
        return json;
    }

    @Override
    public void payNotify(String orderid) {
        InGoldBO inGoldEntity = inGoldMapper.findInGoldByOrderNumber(orderid);
        if (inGoldEntity.getState() == PayStateEnum.UNPAY.getValue()) {
            Integer transactionType = managerMapper.getTransactionTypeByManagerId(inGoldEntity.getManagerid());
            ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(inGoldEntity.getManagerid());
            if (sysSettingMapper.getAutoIn() == InGoldAutoReviewEnum.AUTO.getValue()
                    && transactionType == TransactionTypeEnum.BBOOK.getValue()) {
                GoldSokectDTO goldSocketDTO = Mt4PropertiesUtil.getConfigGoldSoketDTO();
                goldSocketDTO.setLogin(Integer.parseInt(inGoldEntity.getLogin()));
                goldSocketDTO.setMoney(MathUtil.roundBigDecimalSetHalfupToInt(0,
                        MathUtil.translateToBigDecimal(inGoldEntity.getDollar()).multiply(new BigDecimal(100))));
                goldSocketDTO.setComment(Constant.IN_GOLD + inGoldEntity.getId());
                String response = SocketUtil.connectMt4(JSONObject.toJSONString(goldSocketDTO));
                if ("0".equals(response)) {
                    EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.IN_REVIEW_SUCCESS);
                    String emailContent = emailTempEntity.getContent();
                    emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                            .replace(EmailConstant.CREATE_TIME,
                                    DateFormatUtil.format(inGoldEntity.getCreateTime(),
                                            DateFormatUtil.SCHEME_DATE_AND_TIME))
                            .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                            .replace(EmailConstant.MONEY,
                                    MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getDollar()));
                    EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting(); // 查询邮件服务器配置
                    try {
                        MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                                emailSettingEntity.getMailPassword(), managerEntity.getEmail(),
                                emailTempEntity.getTitle(), emailContent); // 发送审核通过邮件
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    inGoldEntity.setState(PayStateEnum.PASS.getValue());
                    inGoldMapper.updReviewState(inGoldEntity);
                }
            } else {
                inGoldEntity.setState(PayStateEnum.PAY.getValue());
                inGoldMapper.updReviewStateByResult(inGoldEntity);
                // 2017-9-22 新增查询提醒邮件模板
                EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.IN_REMIND);
                String emailContent = emailTempEntity.getContent();
                emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                        .replace(EmailConstant.ACCOUNT, String.valueOf(managerEntity.getLogin()))
                        .replace(EmailConstant.DOLLAR, MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getDollar()))
                        .replace(EmailConstant.MONEY, MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getMoney()));
                // 2017-9-18 新增发送提醒审核邮件
                EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
                try {
                    MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                            emailSettingEntity.getMailPassword(), sysSettingMapper.getConfigByKey("remindEmail"),
                            emailTempEntity.getTitle(), emailContent);
                } catch (Exception e) {
                    LOG.error("", e);
                }
            }
        }
    }

}
