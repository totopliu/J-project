package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.param.OutGoldReviewParam;
import com.crm.platform.entity.pub.GoldSummationVO;
import com.crm.platform.entity.pub.OutGoldDTO;
import com.crm.platform.entity.pub.StatisticsGoldDTO;

public interface OutGoldMapper {

    List<JSONObject> queryOutGold(@Param("login") Integer login, @Param("ticket") Integer ticket,
            @Param("outDate") String outDate);

    StatisticsGoldDTO findOutGoldTotal(@Param("login") Integer login, @Param("ticket") Integer ticket,
            @Param("outDate") String outDate);

    void save(OutGoldDTO dto);

    List<JSONObject> queryOutGoldReview(OutGoldReviewParam param);

    OutGoldDTO findOutGoldById(@Param("id") Integer id);

    void updReviewState(OutGoldDTO dto);

    double getOutRate();

    GoldSummationVO findOutGoldReviewTotal(OutGoldReviewParam param);

}
