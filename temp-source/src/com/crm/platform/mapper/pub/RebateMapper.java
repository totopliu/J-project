package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.pub.CurrencyPriceBO;
import com.crm.platform.entity.pub.ManagerRebateBO;
import com.crm.platform.entity.pub.RebateBO;
import com.crm.platform.entity.pub.RebateEntity;
import com.crm.platform.entity.pub.RebatePlanCurrencyBO;
import com.crm.platform.entity.pub.RebatePlanCurrencyVO;
import com.crm.platform.entity.pub.RebatePlanDTO;
import com.crm.platform.entity.pub.RebatePlanLevelBO;
import com.crm.platform.entity.pub.RebatePlanSettingBO;
import com.crm.platform.entity.pub.RebateTransferReviewDTO;
import com.crm.platform.entity.pub.StatisticsGoldDTO;

public interface RebateMapper {

    List<JSONObject> queryUnderlingRebateList(@Param("managerId") Integer managerId);

    RebateEntity findByCidAndMid(@Param("currencyId") Integer currencyId, @Param("managerId") Integer managerId);

    void updFixedById(RebateEntity rebateEntity);

    void insertFixed(RebateEntity rebateEntity);

    void updPointById(RebateEntity rebateEntity);

    void insertPoint(RebateEntity rebateEntity);

    Integer countFixed(@Param("belongId") Integer belongId, @Param("currencyId") Integer currencyId);

    Integer countPoint(@Param("belongId") Integer belongId, @Param("currencyId") Integer currencyId);

    Double findBelongFixed(@Param("belongId") Integer belongId, @Param("currencyId") Integer currencyId);

    int findBelongPoint(@Param("belongId") Integer belongId, @Param("currencyId") Integer currencyId);

    RebateEntity findRebateByMidAndSymbol(@Param("managerId") Integer managerId, @Param("symbol") String symbol);

    void insertManagerRebate(ManagerRebateBO managerRebateBO);

    List<JSONObject> findManagerRebateList(@Param("login") Integer login, @Param("rebateDate") String rebateDate,
            @Param("ticket") String ticket, @Param("over") String over);

    double countRebateTotal(@Param("ids") String ids);

    void updRebateOver(@Param("ids") String ids);

    Integer countUnderlingFixed(@Param("managerId") Integer managerId, @Param("currencyId") Integer currencyId);

    Double findMaxUnderlingFixed(@Param("managerId") Integer managerId, @Param("currencyId") Integer currencyId);

    Integer countUnderlingPoint(@Param("managerId") Integer managerId, @Param("currencyId") Integer currencyId);

    Double findMaxUnderlingPoint(@Param("managerId") Integer managerId, @Param("currencyId") Integer currencyId);

    void updRebateOverByLogin(@Param("login") Integer login);

    StatisticsGoldDTO getRebateTotal(@Param("login") Integer login, @Param("ticket") String ticket,
            @Param("rebateDate") String rebateDate, @Param("over") String over);

    List<RebatePlanSettingBO> listRebatePlanSettingBO(@Param("planLevel") int planLevel, @Param("planId") int planId);

    void saveRebateSetting(RebatePlanSettingBO rebatePlanSettingBO);

    List<JSONObject> listRebatePlan();

    RebatePlanDTO getRebatePlan(Integer id);

    void updateRebatePlan(RebatePlanDTO dto);

    void saveRebatePlan(RebatePlanDTO dto);

    List<RebatePlanLevelBO> listRebatePlanLevel(int planId);

    void removeRebatePlanCurrency(int levelId);

    void removeRebatePlanLevel(int planId);

    void removeRebatePlan(int id);

    List<RebatePlanCurrencyVO> listRebatePlanCurrency(int levelId);

    void saveRebatePlanLevel(RebatePlanLevelBO rebatePlanLevelBO);

    void saveRebatePlanCurrency(RebatePlanCurrencyBO rebatePlanCurrencyBO);

    void removeRebateSetting(Integer managerid);

    void insertRebateBO(RebateBO rebateBO);

    List<JSONObject> listTransferRebate(@Param("managerid") Integer managerid);

    void insertTransferRebate(@Param("managerid") Integer managerid, @Param("dollar") String dollar);

    List<JSONObject> listTransferRebateToReview();

    RebateTransferReviewDTO getRebateTransferDTO(@Param("id") Integer id);

    void saveRebateTransferReview(@Param("id") Integer id, @Param("state") Integer state,
            @Param("reason") String reason);

    int countManagerRebateSetting(@Param("managerId") Integer managerId);

    List<RebatePlanCurrencyBO> listRebatePlanCurrencyBO(@Param("planId") int planId, @Param("level") int level);

    CurrencyPriceBO getCurrencyPriceBOBySymbol(@Param("symbol") String symbol);

    Double getDailyRebateSumByManagerid(@Param("managerid") Integer managerid);

}
