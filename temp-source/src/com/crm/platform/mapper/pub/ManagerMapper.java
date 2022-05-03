package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.param.UnderlingParam;
import com.crm.platform.entity.pub.CountryDO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.ManagerRebateConfigBO;
import com.crm.platform.entity.pub.ManagerRelAndStateBO;
import com.crm.platform.entity.pub.UnderlingAgentTotalVO;
import com.crm.platform.entity.pub.UnderlingTotalVO;

public interface ManagerMapper extends Mapper<ManagerEntity> {
    List<JSONObject> queryManagerForList(@Param("name") String name, @Param("login") String login);

    void update(ManagerEntity dto);

    Integer checkAccount(ManagerEntity dto);

    int countReview(ManagerEntity dto);

    List<JSONObject> queryManagerForListToReview(@Param("name") String name, @Param("account") String account);

    List<JSONObject> queryManagerForListToUnderling(UnderlingParam param);

    JSONObject selectByPrimaryKeyToReview(@Param("managerid") Long id);

    List<JSONObject> findUnderlingList(@Param("managerid") Integer managerid);

    Integer countAdmin(@Param("belongId") Integer belongId);

    List<JSONObject> findUnderLingTree(@Param("managerid") Integer managerid);

    ManagerRelAndStateBO findBelongIdByLogin(Integer login);

    ManagerRelAndStateBO findMidAndBidByBid(Integer belongId);

    List<JSONObject> queryAllUnderling(@Param("managerids") String managerids);

    void updAutoRebate(ManagerEntity dto);

    Double findBalanceByLogin(Integer login);

    Double findRebateByManagerId(@Param("managerid") Integer managerid);

    Double findRebateByLogin(@Param("login") Integer login);

    int getPlanIdByManagerId(Integer managerId);

    List<Integer> getManagerIdByPlanAndLevel(@Param("planId") int planId, @Param("level") int level);

    List<ManagerEntity> listReviewedManager();

    Integer getRebateLoginByLogin(Integer login);

    ManagerRebateConfigBO getManagerRebateConfigBOByLogin(Integer login);

    ManagerRebateConfigBO getManagerRebateConfigBOByManagerId(@Param("managerId") Integer managerId);

    int countSonManagerById(@Param("managerid") Integer managerid, @Param("level") Integer level);

    Integer getTransactionTypeByManagerId(@Param("managerid") Integer managerid);

    UnderlingTotalVO getUnderlingTotal(UnderlingParam param);

    List<UnderlingAgentTotalVO> getUnderlingAgentTotal(UnderlingParam param);

    List<Integer> getSonLoginsByLogin(String login);

    List<CountryDO> listCountry();

    int checkAccountWithoutSelf(ManagerEntity dto);

    List<Integer> listSonLoginsById(@Param("managerId") Integer managerid);

}
