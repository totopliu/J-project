package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.pub.GoldSummationVO;
import com.crm.platform.entity.pub.InGoldBO;
import com.crm.platform.entity.pub.InGoldReviewDTO;
import com.crm.platform.entity.pub.StatisticsGoldDTO;

public interface InGoldMapper {

    List<JSONObject> listInGold(@Param("login") Integer login, @Param("ticket") Integer ticket,
            @Param("inDate") String inDate);

    StatisticsGoldDTO findInGoldTotal(@Param("login") Integer login, @Param("ticket") Integer ticket,
            @Param("inDate") String inDate);

    void save(InGoldBO dto);

    List<JSONObject> queryInGoldReview(InGoldReviewDTO inGoldReviewDTO);

    InGoldBO findInGoldById(@Param("id") Integer id);

    void updReviewState(InGoldBO dto);

    Double findRate();

    void updReviewStateByResult(InGoldBO inGoldEntity);

    GoldSummationVO findInGoldReviewTotal(InGoldReviewDTO inGoldReviewDTO);

    InGoldBO findInGoldByOrderNumber(@Param("orderNumber") String orderNumber);

}
