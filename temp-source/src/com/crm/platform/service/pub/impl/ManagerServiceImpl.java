package com.crm.platform.service.pub.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.constant.EmailConstant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.UnderlingParam;
import com.crm.platform.entity.pub.CountryDO;
import com.crm.platform.entity.pub.EmailSettingDO;
import com.crm.platform.entity.pub.EmailTempDO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.UnderlingAgentTotalVO;
import com.crm.platform.entity.pub.UnderlingTotalVO;
import com.crm.platform.entity.pub.UserResetPassSocketDTO;
import com.crm.platform.entity.pub.UserSocketDTO;
import com.crm.platform.entity.pub.RoleEntity;
import com.crm.platform.enums.ReviewEnum;
import com.crm.platform.enums.TransactionTypeEnum;
import com.crm.platform.mapper.pub.EmailTempMapper;
import com.crm.platform.mapper.pub.GroupTypeMapper;
import com.crm.platform.mapper.pub.ManagerMapper;
import com.crm.platform.mapper.pub.RoleMapper;
import com.crm.platform.mapper.pub.SysSettingMapper;
import com.crm.platform.service.BaseService;
import com.crm.platform.service.pub.ManagerService;
import com.crm.util.DateFormatUtil;
import com.crm.util.MD5Utils;
import com.crm.util.MailUtils;
import com.crm.util.MsgUtil;
import com.crm.util.Mt4PropertiesUtil;
import com.crm.util.PasswordHelper;
import com.crm.util.PasswordUtil;
import com.crm.util.SocketUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class ManagerServiceImpl extends BaseService<ManagerEntity> implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private EmailTempMapper emailTempMapper;

    @Autowired
    private GroupTypeMapper groupTypeMapper;

    @Autowired
    private SysSettingMapper sysSettingMapper;

    private final static Logger LOG = LoggerFactory.getLogger(ManagerServiceImpl.class);

    @Override
    public ManagerEntity getByAccount(String account) {
        if (account == null) {
            account = "";
        }
        Example example = new Example(ManagerEntity.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", account);
        List<ManagerEntity> list = this.managerMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public ManagerEntity selectByPrimaryKey(Integer key) {
        return this.managerMapper.selectByPrimaryKey(key);
    }

    @Override
    public PageInfo<JSONObject> listManager(DataGrid grid, String name, String login) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(this.managerMapper.queryManagerForList(name, login));
    }

    @Override
    public AjaxJson batchDelete(int[] ids) {
        AjaxJson ajaxJson = new AjaxJson();
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                this.managerMapper.deleteByPrimaryKey(ids[i]);
            }
        }
        ajaxJson.setSuccess(true);
        ajaxJson.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_DELETE));
        return ajaxJson;
    }

    @Override
    public AjaxJson save(ManagerEntity dto, String lg) {
        AjaxJson json = new AjaxJson();
        PasswordHelper helper = new PasswordHelper();
        if (dto.getManagerid() == null) {
            if (this.managerMapper.checkAccount(dto) > 0) {
                json.setSuccess(false);
                json.setMsg(MsgUtil.getLgMsg(Constant.REGISTERED_ERROR_MESSAGE_ACCOUNT, lg));
                return json;
            }
            if (dto.getRole() == null) {
                // 设置默认注册角色
                RoleEntity roleEntity = roleMapper.selectDefaultRole();
                if (roleEntity != null) {
                    dto.setRole(roleEntity.getId());
                }
            }
            helper.encryptPassword(dto);
            dto.setCreatetime(new Date());
            this.managerMapper.insertSelective(dto);
            // 2017-9-22 新增查询提醒邮件模板
            EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.REG_REMIND);
            String emailcontent = emailTempEntity.getContent();
            emailcontent = emailcontent.replace(EmailConstant.USER_NAME, dto.getName())
                    .replace(EmailConstant.USER_ENAME, dto.getEname()).replace(EmailConstant.CREATE_TIME,
                            DateFormatUtil.format(new Date(), DateFormatUtil.SCHEME_DATE_AND_TIME));
            // 2017-9-18 新增发送提醒审核邮件
            EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
            try {
                MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                        emailSettingEntity.getMailPassword(), sysSettingMapper.getConfigByKey("remindEmail"),
                        emailTempEntity.getTitle(), emailcontent);
            } catch (Exception e) {
                LOG.error("", e);
            }
        } else {
            if (this.managerMapper.checkAccountWithoutSelf(dto) > 0) {
                json.setSuccess(false);
                json.setMsg(MsgUtil.getLgMsg(Constant.REGISTERED_ERROR_MESSAGE_ACCOUNT, lg));
                return json;
            } else {
                dto.setEmail(dto.getAccount());
            }
            if (StringUtils.isNotEmpty(dto.getPassword())) {
                helper.encryptPassword(dto);
            } else {
                dto.setPassword(null);
            }
            // 判断用户是否设置为代理用户
            if (dto.getType() == 1) {
                ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(dto.getManagerid());
                ManagerEntity parentManagerEntity = managerMapper.selectByPrimaryKey(managerEntity.getBelongid());
                if (dto.getLevel() <= parentManagerEntity.getLevel()) {
                    json.setSuccess(false);
                    json.setMsg(MsgUtil.getLgMsg(Constant.LEVEL_MORE, lg));
                    return json;
                }
                int countSonManager = managerMapper.countSonManagerById(dto.getManagerid(), dto.getLevel());
                if (countSonManager > 0) {
                    json.setSuccess(false);
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.LEVEL_LESS));
                    return json;
                }
                if (managerEntity.getRebateLogin() == null) {
                    dto.setRebateLogin(managerEntity.getLogin());
                    dto.setRebateLoginPwd(managerEntity.getLoginPwd());
                }
            }
            this.managerMapper.updateByPrimaryKeySelective(dto);
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        }
        json.setSuccess(true);
        return json;
    }

    @Override
    public void update(ManagerEntity dto) {
        this.managerMapper.updateByPrimaryKeySelective(dto);
    }

    @Override
    public int getReviewState(ManagerEntity dto) {
        return this.managerMapper.countReview(dto);
    }

    @Override
    public PageInfo<JSONObject> listManagerToReview(DataGrid grid, String name, String account) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(this.managerMapper.queryManagerForListToReview(name, account));
    }

    @Override
    public AjaxJson updateManagerReview(ManagerEntity dto, String group) {
        AjaxJson json = new AjaxJson();
        int managerId = dto.getManagerid();
        ManagerEntity managerEntity = managerMapper.selectByPrimaryKey(managerId);
        if (dto.getReviewState() == ReviewEnum.PASS.getValue()) {
            Integer transactionType = groupTypeMapper.getTypeByGroupName(group);
            if (transactionType == null) {
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(Constant.TRANSACTION_TYPE_ERROR));
                return json;
            }
            // 判断返佣等级是否正确
            ManagerEntity parentManagerEntity;
            if (dto.getBelongid() != null) {
                parentManagerEntity = managerMapper.selectByPrimaryKey(dto.getBelongid());
            } else {
                parentManagerEntity = managerMapper.selectByPrimaryKey(managerEntity.getBelongid());
            }
            if (dto.getType() == 1) {
                if (dto.getLevel() <= parentManagerEntity.getLevel()) {
                    json.setSuccess(false);
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.LEVEL_MORE));
                    return json;
                }
            }
            // 根据交易类型创建不同的注册参数
            UserSocketDTO userSocketDTO;
            int login;
            if (transactionType == TransactionTypeEnum.ABOOK.getValue()) {
                userSocketDTO = Mt4PropertiesUtil.getEmptyUserSocketForA();
                login = Integer.parseInt(sysSettingMapper.getConfigByKey("alogin")) + 1;
                sysSettingMapper.updateSysConfig("alogin", String.valueOf(login));
            } else if (transactionType == TransactionTypeEnum.BBOOK.getValue()) {
                userSocketDTO = Mt4PropertiesUtil.getEmptyUserSocketForB();
                login = Integer.parseInt(sysSettingMapper.getConfigByKey("blogin")) + 1;
                sysSettingMapper.updateSysConfig("blogin", String.valueOf(login));
            } else {
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(Constant.REGISTERED_ERROR_MESSAGE_TRACTYPE));
                return json;
            }
            String loginPwd = PasswordUtil.genRandomNum(10);
            String passgc = PasswordUtil.genRandomNum(10);
            userSocketDTO.setUsergroup(group);
            userSocketDTO.setUsername(managerEntity.getEname() + "\0");
            userSocketDTO.setPassword(loginPwd);
            userSocketDTO.setLogin(login);
            userSocketDTO.setUseremail(managerEntity.getEmail());
            userSocketDTO.setPassgc(passgc);
            String paramJsonStr = JSONObject.toJSONString(userSocketDTO);
            String response = SocketUtil.connectMt4(paramJsonStr);
            if ("0".equals(response) || 0 >= Integer.parseInt(response)) {
                json.setSuccess(false);
                if (-7 == Integer.parseInt(response)) {
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.MT4_REGISTERED_FAIL_SEVEN));
                } else if (-8 == Integer.parseInt(response)) {
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.MT4_REGISTERED_FAIL_EIGHT));
                } else if (-10 == Integer.parseInt(response)) {
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.MT4_REGISTERED_FAIL_TEN));
                } else {
                    json.setMsg(MsgUtil.getSessionLgMsg(Constant.MT4_REGISTERED_FAIL));
                }
                return json;
            }
            dto.setLogin(login);
            dto.setLoginPwd(loginPwd);
            dto.setPassgc(passgc);
            // 如果代理用户，交易账号与返佣账号一样
            if (dto.getType() == 1) {
                dto.setRebateLogin(login);
                dto.setRebateLoginPwd(loginPwd);
            }
            EmailTempDO emailTempEntity;
            String emailContent;
            String emailTitle;
            if (dto.getRebateLogin() != null) {
                emailTempEntity = emailTempMapper.fingByCode(EmailConstant.REG_REVIEW_SUCCESS);
                if (managerEntity.getCountry() == 1 || managerEntity.getCountry() == null) {
                    emailContent = emailTempEntity.getContent();
                    emailTitle = emailTempEntity.getTitle();
                } else {
                    emailContent = emailTempEntity.getEcontent();
                    emailTitle = emailTempEntity.getEtitle();
                }
                emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                        .replace(EmailConstant.ACCOUNT, String.valueOf(login)).replace(EmailConstant.PASSWORD, loginPwd)
                        .replace(EmailConstant.PASSGC, passgc)
                        .replace(EmailConstant.REBATE_ACCOUNT, String.valueOf(dto.getRebateLogin()))
                        .replace(EmailConstant.REBATE_PASSWORD, dto.getRebateLoginPwd());
            } else {
                emailTempEntity = emailTempMapper.fingByCode(EmailConstant.REG_REVIEW_COMMON_SUCCESS);
                if (managerEntity.getCountry() == 1 || managerEntity.getCountry() == null) {
                    emailContent = emailTempEntity.getContent();
                    emailTitle = emailTempEntity.getTitle();
                } else {
                    emailContent = emailTempEntity.getEcontent();
                    emailTitle = emailTempEntity.getEtitle();
                }
                emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName())
                        .replace(EmailConstant.ACCOUNT, String.valueOf(login)).replace(EmailConstant.PASSWORD, loginPwd)
                        .replace(EmailConstant.PASSGC, passgc);
            }
            EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
            try {
                MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                        emailSettingEntity.getMailPassword(), managerEntity.getEmail(), emailTitle, emailContent);
            } catch (Exception e) {
                LOG.error("", e);
                json.setSuccess(false);
                json.setMsg(EmailConstant.EMAIL_FAIL);
                return json;
            }
        } else {
            EmailTempDO emailTempEntity = emailTempMapper.fingByCode(EmailConstant.REG_REVIEW_FAIL);
            String emailContent;
            String emailTitle;
            if (managerEntity.getCountry() == 1 || managerEntity.getCountry() == null) {
                emailContent = emailTempEntity.getContent();
                emailTitle = emailTempEntity.getTitle();
            } else {
                emailContent = emailTempEntity.getEcontent();
                emailTitle = emailTempEntity.getEtitle();
            }
            emailContent = emailContent.replace(EmailConstant.USER_NAME, managerEntity.getName());
            emailContent = emailContent.replace(EmailConstant.REASON, dto.getReviewReason());
            EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
            try {
                MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                        emailSettingEntity.getMailPassword(), managerEntity.getEmail(), emailTitle, emailContent);
            } catch (Exception e) {
                LOG.error("", e);
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(EmailConstant.EMAIL_FAIL));
                return json;
            }
        }
        this.managerMapper.updateByPrimaryKeySelective(dto);
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        return json;
    }

    @Override
    public PageInfo<JSONObject> listManagerToUnderling(DataGrid grid, Integer managerid, UnderlingParam param) {
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
        if (managerids.length() == 0) {
            param.setManagerids("0");
        } else {
            param.setManagerids(managerids.toString());
        }
        return new PageInfo<JSONObject>(this.managerMapper.queryManagerForListToUnderling(param));
    }

    /**
     * 递归方法（获取当前登录用户的所有下级ID）
     * 
     * @param managerid
     * @return
     */
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
    public JSONObject getByIdToReview(Long id) {
        return this.managerMapper.selectByPrimaryKeyToReview(id);
    }

    @Override
    public AjaxJson getUnderling(Integer managerid) {
        AjaxJson json = new AjaxJson();
        json.setSuccess(true);
        json.setData(managerMapper.findUnderlingList(managerid));
        return json;
    }

    @Override
    public AjaxJson getUnderlingTreeJson(Integer managerid) {
        List<JSONObject> list = managerMapper.findUnderLingTree(managerid);
        for (JSONObject jsonObject : list) {
            managerid = jsonObject.getInteger("val");
            jsonObject = getUnderlingTree(jsonObject, managerid);
        }
        AjaxJson json = new AjaxJson();
        json.setSuccess(true);
        json.setData(list);
        return json;
    }

    /**
     * 递归方法（获取当前用户的所有下级的树形结构）
     * 
     * @param jsonObject
     * @param managerid
     * @return
     */
    private JSONObject getUnderlingTree(JSONObject jsonObject, Integer managerid) {
        List<JSONObject> list = managerMapper.findUnderLingTree(managerid);
        if (list != null && list.size() > 0) {
            jsonObject.put("nodes", list);
            for (JSONObject jsonObject1 : list) {
                managerid = jsonObject1.getInteger("val");
                jsonObject = getUnderlingTree(jsonObject1, managerid);
            }
        }
        return jsonObject;
    }

    @Override
    public AjaxJson updateProfileInfo(ManagerEntity dto) {
        AjaxJson json = new AjaxJson();
        this.managerMapper.updateByPrimaryKeySelective(dto);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson updateAutoRebate(ManagerEntity dto) {
        managerMapper.updAutoRebate(dto);
        AjaxJson json = new AjaxJson();
        json.setSuccess(true);
        return json;
    }

    @Override
    public Double getBalanceByLogin(Integer login) {
        return managerMapper.findBalanceByLogin(login);
    }

    @Override
    public AjaxJson updateProfileSetting(Integer autoRebate, Integer managerid) {
        ManagerEntity dto = new ManagerEntity();
        dto.setManagerid(managerid);
        dto.setAutoRebate(autoRebate);
        managerMapper.updAutoRebate(dto);
        AjaxJson json = new AjaxJson();
        json.setSuccess(true);
        return json;
    }

    @Override
    public double getRebateByManagerId(Integer managerid) {
        return managerMapper.findRebateByManagerId(managerid);
    }

    @Override
    public ManagerEntity getUnderlingManger(int managerId) {
        return managerMapper.selectByPrimaryKey(managerId);
    }

    @Override
    public AjaxJson listReviewedManager() {
        AjaxJson json = new AjaxJson();
        List<ManagerEntity> managerEntities = managerMapper.listReviewedManager();
        json.setData(managerEntities);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson updatePassword(Integer managerid, String password) {
        AjaxJson json = new AjaxJson();
        ManagerEntity dto = managerMapper.selectByPrimaryKey(managerid);
        PasswordHelper helper = new PasswordHelper();
        if (StringUtils.isNotEmpty(password)) {
            dto.setPassword(password);
            helper.encryptPassword(dto);
        } else {
            dto.setPassword(null);
        }
        managerMapper.updateByPrimaryKeySelective(dto);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson settingBelong(Integer managerId, Integer belongId) {
        AjaxJson json = new AjaxJson();
        ManagerEntity commonUser = managerMapper.selectByPrimaryKey(managerId);
        ManagerEntity agentUser = managerMapper.selectByPrimaryKey(belongId);
        if (commonUser.getType() == 1) {
            if (commonUser.getLevel() <= agentUser.getLevel()) {
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(Constant.LEVEL_MORE));
                return json;
            }
        }
        commonUser.setBelongid(belongId);
        managerMapper.updateByPrimaryKeySelective(commonUser);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson updateRebateLogin(Integer managerId, Integer rebateLogin, String rebateLoginPwd) {
        AjaxJson json = new AjaxJson();
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setManagerid(managerId);
        managerEntity.setRebateLogin(rebateLogin);
        managerEntity.setRebateLoginPwd(rebateLoginPwd);
        managerMapper.updateByPrimaryKeySelective(managerEntity);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson sendEmail(Integer id) {
        AjaxJson json = new AjaxJson();
        ManagerEntity manager = managerMapper.selectByPrimaryKey(id);
        EmailTempDO emailTempEntity;
        String emailContent;
        if (manager.getReviewState() == 1) { // review pass
            if (manager.getRebateLogin() != null) { // manager is agent
                emailTempEntity = emailTempMapper.fingByCode(EmailConstant.REG_REVIEW_SUCCESS);
                emailContent = emailTempEntity.getContent();
                emailContent = emailContent.replace(EmailConstant.USER_NAME, manager.getName())
                        .replace(EmailConstant.ACCOUNT, String.valueOf(manager.getLogin()))
                        .replace(EmailConstant.PASSWORD, manager.getLoginPwd())
                        .replace(EmailConstant.PASSGC, manager.getPassgc())
                        .replace(EmailConstant.REBATE_ACCOUNT, String.valueOf(manager.getRebateLogin()))
                        .replace(EmailConstant.REBATE_PASSWORD, manager.getRebateLoginPwd());
            } else { // manager is common
                emailTempEntity = emailTempMapper.fingByCode(EmailConstant.REG_REVIEW_COMMON_SUCCESS);
                emailContent = emailTempEntity.getContent();
                emailContent = emailContent.replace(EmailConstant.USER_NAME, manager.getName())
                        .replace(EmailConstant.ACCOUNT, String.valueOf(manager.getLogin()))
                        .replace(EmailConstant.PASSWORD, manager.getLoginPwd())
                        .replace(EmailConstant.PASSGC, (manager.getPassgc() == null) ? "" : manager.getPassgc());
            }
            EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
            try {
                MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                        emailSettingEntity.getMailPassword(), manager.getEmail(), emailTempEntity.getTitle(),
                        emailContent);
            } catch (Exception e) {
                LOG.error("", e);
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(EmailConstant.EMAIL_FAIL));
                return json;
            }
        } else if (manager.getReviewState() == 2) { // review no pass
            emailTempEntity = emailTempMapper.fingByCode(EmailConstant.REG_REVIEW_FAIL);
            emailContent = emailTempEntity.getContent();
            emailContent = emailContent.replace(EmailConstant.USER_NAME, manager.getName());
            emailContent = emailContent.replace(EmailConstant.REASON, manager.getReviewReason());
            EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
            try {
                MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                        emailSettingEntity.getMailPassword(), manager.getEmail(), emailTempEntity.getTitle(),
                        emailContent);
            } catch (Exception e) {
                LOG.error("", e);
                json.setSuccess(false);
                json.setMsg(MsgUtil.getSessionLgMsg(EmailConstant.EMAIL_FAIL));
                return json;
            }
        }
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson getUnderlingTotal(UnderlingParam param, Integer managerid) {
        AjaxJson json = new AjaxJson();
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
        if (managerids.length() == 0) {
            param.setManagerids("0");
        } else {
            param.setManagerids(managerids.toString());
        }
        UnderlingTotalVO underlingTotalVO = managerMapper.getUnderlingTotal(param);
        json.setData(underlingTotalVO);
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson getUnderlingAgentTotal(UnderlingParam param, Integer managerid) {
        AjaxJson json = new AjaxJson();
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
        if (managerids.length() == 0) {
            param.setManagerids("0");
        } else {
            param.setManagerids(managerids.toString());
        }
        List<UnderlingAgentTotalVO> underlingAgentTotalVOs = managerMapper.getUnderlingAgentTotal(param);
        json.setSuccess(true);
        json.setData(underlingAgentTotalVOs);
        return json;
    }

    @Override
    public List<CountryDO> listCountry() {
        return managerMapper.listCountry();
    }

    @Override
    public AjaxJson sendVerification(String account, String lg) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isEmpty(account)) {
            json.setMsg(MsgUtil.getLgMsg(Constant.ACCOUNT_EMPTY, lg));
            json.setSuccess(false);
            return json;
        }
        ManagerEntity entity = new ManagerEntity();
        entity.setAccount(account);
        ManagerEntity manager = managerMapper.selectOne(entity);
        if (manager == null) {
            json.setMsg(MsgUtil.getLgMsg(Constant.ACCOUNT_NOT_EXIST, lg));
            json.setSuccess(false);
            return json;
        }
        String tamp = String.valueOf(Calendar.getInstance().getTime().getTime());
        String managerid = String.valueOf(manager.getManagerid());
        String hash = MD5Utils.md5(tamp + managerid);
        String resetUrl = new StringBuffer(Constant.RESET_PASS_URL).append("_").append(hash).append("_")
                .append(managerid).append("_").append(tamp).toString();

        String title;
        String content;

        if (manager.getCountry() != null && manager.getCountry() != 1) {
            title = "CRM password to retrieve verification";
            content = "CRM password reset address: <a target=\"_blank\" href=\"" + resetUrl + "\">Click to go</a>";
        } else {
            title = "CRM密码找回验证";
            content = "CRM密码重置地址：<a target=\"_blank\" href=\"" + resetUrl + "\">点击前往</a>";
        }

        EmailSettingDO emailSettingEntity = emailTempMapper.getEmailSetting();
        try {
            MailUtils.send(emailSettingEntity.getSmtpServer(), emailSettingEntity.getMailAccount(),
                    emailSettingEntity.getMailPassword(), manager.getEmail(), title, content);
        } catch (Exception e) {
            LOG.error("", e);
            json.setSuccess(false);
            json.setMsg(MsgUtil.getLgMsg(EmailConstant.EMAIL_FAIL, lg));
            return json;
        }
        json.setMsg(MsgUtil.getLgMsg(Constant.EMAIL_HAS_BEEN_SENT, lg));
        json.setSuccess(true);
        return json;
    }

    @Override
    public AjaxJson resetPass(String hash, String managerid, String tamp, String password, String lg) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isEmpty(hash) || StringUtils.isEmpty(managerid) || StringUtils.isEmpty(tamp)) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getLgMsg(Constant.LINK_ADDR_INCORRECT, lg));
            return json;
        }
        if (StringUtils.isEmpty(password)) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getLgMsg(Constant.PASSWORD_EMPTY, lg));
            return json;
        }
        if (!hash.equals(MD5Utils.md5(tamp + managerid))) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getLgMsg(Constant.LINK_ADDR_INCORRECT, lg));
            return json;
        }
        if ((Calendar.getInstance().getTime().getTime() - Long.parseLong(tamp)) > (30 * 60 * 1000)) {
            json.setSuccess(false);
            json.setMsg(MsgUtil.getLgMsg(Constant.LINK_OUTTIME, lg));
            return json;
        }
        PasswordHelper helper = new PasswordHelper();
        ManagerEntity dto = managerMapper.selectByPrimaryKey(Integer.parseInt(managerid));
        dto.setPassword(password);
        helper.encryptPassword(dto);
        managerMapper.updateByPrimaryKeySelective(dto);
        json.setSuccess(true);
        json.setMsg(MsgUtil.getLgMsg(Constant.RESET_SUCCESS, lg));
        return json;
    }

    @Override
    public AjaxJson updateMTPass(Integer managerid, String password) {
        AjaxJson json = new AjaxJson();
        Integer transactionType = managerMapper.getTransactionTypeByManagerId(managerid);
        UserResetPassSocketDTO user = null;
        if (transactionType == TransactionTypeEnum.ABOOK.getValue()) {
            user = Mt4PropertiesUtil.getEmptyUserResetPassSocketForA();
        } else if (transactionType == TransactionTypeEnum.BBOOK.getValue()) {
            user = Mt4PropertiesUtil.getEmptyUserResetPassSocketForB();
        }
        if (user != null) {
            user.setType(0);
            ManagerEntity manager = managerMapper.selectByPrimaryKey(managerid);
            if (manager.getLogin() == null) {
                json.setSuccess(false);
                return json;
            }
            user.setLogin(manager.getLogin());
            user.setPassword(password);
            String paramJsonStr = JSONObject.toJSONString(user);
            String response = SocketUtil.connectMt4(paramJsonStr);
            if ("0".equals(response)) {
                manager.setLoginPwd(password);
                managerMapper.updateByPrimaryKeySelective(manager);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
            }
        } else {
            json.setSuccess(false);
        }
        return json;
    }

    @Override
    public AjaxJson updateMTGcPass(Integer managerid, String password) {
        AjaxJson json = new AjaxJson();
        Integer transactionType = managerMapper.getTransactionTypeByManagerId(managerid);
        UserResetPassSocketDTO user = null;
        if (transactionType == TransactionTypeEnum.ABOOK.getValue()) {
            user = Mt4PropertiesUtil.getEmptyUserResetPassSocketForA();
        } else if (transactionType == TransactionTypeEnum.BBOOK.getValue()) {
            user = Mt4PropertiesUtil.getEmptyUserResetPassSocketForB();
        }
        if (user != null) {
            user.setType(1);
            ManagerEntity manager = managerMapper.selectByPrimaryKey(managerid);
            if (manager.getLogin() == null) {
                json.setSuccess(false);
                return json;
            }
            user.setLogin(manager.getLogin());
            user.setPassword(password);
            String paramJsonStr = JSONObject.toJSONString(user);
            String response = SocketUtil.connectMt4(paramJsonStr);
            if ("0".equals(response)) {
                manager.setPassgc(password);
                managerMapper.updateByPrimaryKeySelective(manager);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
            }
        } else {
            json.setSuccess(false);
        }
        return json;
    }
}
