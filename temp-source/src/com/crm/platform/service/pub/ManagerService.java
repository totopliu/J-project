package com.crm.platform.service.pub;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.UnderlingParam;
import com.crm.platform.entity.pub.CountryDO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.github.pagehelper.PageInfo;

public interface ManagerService {
    ManagerEntity getByAccount(String account);

    public ManagerEntity selectByPrimaryKey(Integer key);

    PageInfo<JSONObject> listManager(DataGrid grid, String name, String login);

    AjaxJson batchDelete(int[] ids);

    AjaxJson save(ManagerEntity dto, String lg);

    void update(ManagerEntity dto);

    int getReviewState(ManagerEntity dto);

    PageInfo<JSONObject> listManagerToReview(DataGrid grid, String name, String account);

    AjaxJson updateManagerReview(ManagerEntity dto, String group);

    PageInfo<JSONObject> listManagerToUnderling(DataGrid grid, Integer managerid, UnderlingParam param);

    JSONObject getByIdToReview(Long id);

    AjaxJson getUnderling(Integer managerid);

    AjaxJson getUnderlingTreeJson(Integer managerid);

    AjaxJson updateProfileInfo(ManagerEntity dto);

    AjaxJson updateAutoRebate(ManagerEntity dto);

    Double getBalanceByLogin(Integer login);

    AjaxJson updateProfileSetting(Integer autoRebate, Integer managerid);

    double getRebateByManagerId(Integer managerid);

    ManagerEntity getUnderlingManger(int managerId);

    AjaxJson listReviewedManager();

    AjaxJson updatePassword(Integer managerid, String password);

    AjaxJson settingBelong(Integer managerId, Integer belongId);

    AjaxJson updateRebateLogin(Integer managerId, Integer rebateLogin, String rebateLoginPwd);

    AjaxJson sendEmail(Integer id);

    AjaxJson getUnderlingTotal(UnderlingParam param, Integer managerid);

    AjaxJson getUnderlingAgentTotal(UnderlingParam param, Integer managerid);

    List<CountryDO> listCountry();

    AjaxJson sendVerification(String account, String lg);

    AjaxJson resetPass(String hash, String managerid, String tamp, String password, String lg);

    AjaxJson updateMTPass(Integer managerid, String password);

    AjaxJson updateMTGcPass(Integer managerid, String password);
}
